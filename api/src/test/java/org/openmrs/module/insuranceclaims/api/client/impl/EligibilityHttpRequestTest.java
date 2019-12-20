package org.openmrs.module.insuranceclaims.api.client.impl;

import org.hamcrest.Matchers;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.EligibilityResponse;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URISyntaxException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class EligibilityHttpRequestTest extends BaseModuleContextSensitiveTest {

    static String BASE_URL = "http://localhost:8000/api_fhir/EligibilityResponse";

    @InjectMocks
    EligibilityHttpRequestImpl request;

    @Mock
    FhirRequestClient client;

    public <T, K extends IBaseResource> void setupPostRequestMock(Class<T> expectedObjectToSend, K response, String url)
            throws URISyntaxException {
        when(client.postObject(eq(url), any(expectedObjectToSend), eq(response.getClass())))
                .thenAnswer(i -> response);
    }

    @Test
    public void sendEligibilityRequest_shouldPassFhirObjectToClient() throws URISyntaxException {
        String expectedUrlCall = BASE_URL + "/";
        EligibilityRequest eligibilityRequest = new EligibilityRequest();
        EligibilityResponse response = new EligibilityResponse();

        setupPostRequestMock(eligibilityRequest.getClass(), response, expectedUrlCall);
        EligibilityResponse result = request.sendEligibilityRequest(BASE_URL, eligibilityRequest);
        Assert.assertThat(result, Matchers.samePropertyValuesAs(response));
    }
}
