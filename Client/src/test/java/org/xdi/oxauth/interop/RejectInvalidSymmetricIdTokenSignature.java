package org.xdi.oxauth.interop;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.xdi.oxauth.BaseTest;
import org.xdi.oxauth.client.*;
import org.xdi.oxauth.model.common.ResponseType;
import org.xdi.oxauth.model.crypto.signature.SignatureAlgorithm;
import org.xdi.oxauth.model.jws.HMACSigner;
import org.xdi.oxauth.model.jwt.Jwt;
import org.xdi.oxauth.model.register.ApplicationType;
import org.xdi.oxauth.model.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.*;

/**
 * OC5:FeatureTest-Reject Invalid Symmetric ID Token Signature
 *
 * @author Javier Rojas Blum Date: 08.30.2013
 */
public class RejectInvalidSymmetricIdTokenSignature extends BaseTest {

    @Parameters({"redirectUris", "userId", "userSecret", "redirectUri"})
    @Test
    public void rejectInvalidSymmetricIdTokenSignature(final String redirectUris, final String userId,
                                                     final String userSecret, final String redirectUri) throws Exception {
        showTitle("OC5:FeatureTest-Reject Invalid Symmetric ID Token Signature");

        List<ResponseType> responseTypes = Arrays.asList(ResponseType.ID_TOKEN);

        // 1. Registration
        RegisterRequest registerRequest = new RegisterRequest(ApplicationType.WEB, "oxAuth test app",
                StringUtils.spaceSeparatedToList(redirectUris));
        registerRequest.setResponseTypes(responseTypes);
        registerRequest.setIdTokenSignedResponseAlg(SignatureAlgorithm.HS512);

        RegisterClient registerClient = new RegisterClient(registrationEndpoint);
        registerClient.setRequest(registerRequest);
        RegisterResponse registerResponse = registerClient.exec();

        showClient(registerClient);
        assertEquals(registerResponse.getStatus(), 200, "Unexpected response code: " + registerResponse.getEntity());
        assertNotNull(registerResponse.getClientId());
        assertNotNull(registerResponse.getClientSecret());
        assertNotNull(registerResponse.getRegistrationAccessToken());
        assertNotNull(registerResponse.getClientSecretExpiresAt());

        String clientId = registerResponse.getClientId();
        String clientSecret = registerResponse.getClientSecret();

        // 2. Request Authorization
        List<String> scopes = Arrays.asList("openid", "profile", "address", "email");
        String nonce = UUID.randomUUID().toString();
        String state = "STATE_XYZ";

        AuthorizationRequest authorizationRequest = new AuthorizationRequest(responseTypes, clientId, scopes, redirectUri, nonce);
        authorizationRequest.setState(state);

        AuthorizationResponse authorizationResponse = authenticateResourceOwnerAndGrantAccess(
                authorizationEndpoint, authorizationRequest, userId, userSecret);

        assertNotNull(authorizationResponse.getLocation());
        assertNotNull(authorizationResponse.getIdToken());
        assertNotNull(authorizationResponse.getState());

        String idToken = authorizationResponse.getIdToken();

        // 3. Validate id_token
        Jwt jwt = Jwt.parse(idToken);
        HMACSigner hmacSigner = new HMACSigner(SignatureAlgorithm.HS256, clientSecret);
        assertFalse(hmacSigner.validate(jwt));
    }
}