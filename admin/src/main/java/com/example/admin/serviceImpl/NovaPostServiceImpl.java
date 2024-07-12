package com.example.admin.serviceImpl;

import com.example.admin.mapper.NovaPostMapper;
import com.example.admin.model.general.SelectSearchRequest;
import com.example.admin.model.houses.CityResponse;
import com.example.admin.model.houses.StreetResponse;
import com.example.admin.service.NovaPostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class NovaPostServiceImpl implements NovaPostService {
    private final RestTemplate restTemplate;
    private final NovaPostMapper novaPostMapper;
    @Value("${nova-post.api-key}")
    private String apiKey;
    private final String url = "https://api.novaposhta.ua/v2.0/json/";
    private final Logger logger = LogManager.getLogger(NovaPostServiceImpl.class);

    public NovaPostServiceImpl(RestTemplate restTemplate, NovaPostMapper novaPostMapper) {
        this.restTemplate = restTemplate;
        this.novaPostMapper = novaPostMapper;
    }

    @Override
    public Page<CityResponse> getCities(SelectSearchRequest selectSearchRequest) {
        logger.info("getCities() - Getting cities from nova post API "+selectSearchRequest.toString());
        String request = createCityRequest(selectSearchRequest);
        String response = sendRequest(request);
        JSONObject responseJson = new JSONObject(response);
        JSONArray jsonArray = responseJson.getJSONArray("data");
        List<CityResponse> cityResponses = createCityResponseList(jsonArray);
        Pageable pageable = PageRequest.of(selectSearchRequest.page(),10);
        int totalCount = responseJson.getJSONObject("info").getInt("totalCount");
        Page<CityResponse> cityResponsePage = new PageImpl<>(cityResponses, pageable, totalCount);
        logger.info("getCities() - Cities have been got");
        return cityResponsePage;
    }
    private String createCityRequest(SelectSearchRequest selectSearchRequest){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("apiKey", apiKey);
        jsonObject.put("modelName", "Address");
        jsonObject.put("calledMethod", "getCities");
        JSONObject methodProperties = new JSONObject();
        methodProperties.put("FindByString", selectSearchRequest.search());
        methodProperties.put("Limit", 10);
        methodProperties.put("Page", selectSearchRequest.page());
        jsonObject.put("methodProperties", methodProperties);
        return jsonObject.toString();
    }
    private List<CityResponse> createCityResponseList(JSONArray jsonArray){
        List<CityResponse> cityResponses = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject data = jsonArray.getJSONObject(i);
            CityResponse cityResponse = novaPostMapper.createCityResponse(data.getString("Description"), data.getString("Ref"));
            cityResponses.add(cityResponse);
        }
        return cityResponses;
    }

    @Override
    public Page<StreetResponse> getStreets(SelectSearchRequest selectSearchRequest, String cityRef) {
        logger.info("getStreets() - Getting streets from nova post API "+selectSearchRequest.toString()+" cityRef "+cityRef);
        String request = createStreetRequest(selectSearchRequest, cityRef);
        String response = sendRequest(request);
        JSONObject responseJson = new JSONObject(response);
        JSONArray jsonArray = responseJson.getJSONArray("data");
        List<StreetResponse> streetResponses = createStreetResponseList(jsonArray);
        Pageable pageable = PageRequest.of(selectSearchRequest.page(),10);
        int totalCount = responseJson.getJSONObject("info").getInt("totalCount");
        Page<StreetResponse> cityResponsePage = new PageImpl<>(streetResponses, pageable, totalCount);
        logger.info("getStreets() - Streets have been got");
        return cityResponsePage;
    }
    private String createStreetRequest(SelectSearchRequest selectSearchRequest, String cityRef){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("apiKey", apiKey);
        jsonObject.put("modelName", "Address");
        jsonObject.put("calledMethod", "getStreet");
        JSONObject methodProperties = new JSONObject();
        methodProperties.put("CityRef", cityRef);
        methodProperties.put("FindByString", selectSearchRequest.search());
        methodProperties.put("Limit", 10);
        methodProperties.put("Page", selectSearchRequest.page());
        jsonObject.put("methodProperties", methodProperties);
        return jsonObject.toString();
    }
    private List<StreetResponse> createStreetResponseList(JSONArray jsonArray) {
        List<StreetResponse> streetResponses = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject data = jsonArray.getJSONObject(i);
            StreetResponse streetResponse = novaPostMapper.createStreetResponse(data.getString("Description"));
            streetResponses.add(streetResponse);
        }
        return streetResponses;
    }
    private String sendRequest(String request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.exchange(url,
                HttpMethod.POST,
                entity,
                String.class);
        return response.getBody();
    }
}
