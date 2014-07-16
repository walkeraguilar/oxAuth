package org.xdi.oxauth.client;

import org.xdi.oxauth.model.session.EndSessionErrorResponseType;

/**
 * Represents an end session response received from the authorization server.
 *
 * @author Javier Rojas Blum Date: 12.14.2011
 */
public class EndSessionResponse extends BaseResponse {

    private String location;

    private EndSessionErrorResponseType errorType;
    private String errorDescription;
    private String errorUri;

    /**
     * Constructs an end session response.
     *
     * @param status The response status code.
     */
    public EndSessionResponse(int status) {
        super(status);
    }

    /**
     * Returns the location of the response in the header.
     *
     * @return The location of the response.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the response in the header.
     *
     * @param location The location of the response.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the error code when the request fails, otherwise will return <code>null</code>.
     *
     * @return The error code when the request fails.
     */
    public EndSessionErrorResponseType getErrorType() {
        return errorType;
    }

    /**
     * Sets the error code when the request fails, otherwise will return <code>null</code>.
     *
     * @param errorType The error code when the request fails.
     */
    public void setErrorType(EndSessionErrorResponseType errorType) {
        this.errorType = errorType;
    }

    /**
     * Returns a human-readable UTF-8 encoded text providing additional
     * information, used to assist the client developer in understanding the
     * error that occurred.
     *
     * @return The error description.
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Sets a human-readable UTF-8 encoded text providing additional
     * information, used to assist the client developer in understanding the
     * error that occurred.
     *
     * @param errorDescription The error description.
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * Returns a URI identifying a human-readable web page with information
     * about the error, used to provide the client developer with additional
     * information about the error.
     *
     * @return A URI with information about the error.
     */
    public String getErrorUri() {
        return errorUri;
    }

    /**
     * Sets a URI identifying a human-readable web page with information about
     * the error, used to provide the client developer with additional
     * information about the error.
     *
     * @param errorUri A URI with information about the error.
     */
    public void setErrorUri(String errorUri) {
        this.errorUri = errorUri;
    }
}