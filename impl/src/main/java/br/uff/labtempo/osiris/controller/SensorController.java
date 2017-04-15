package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.omcp.common.exceptions.client.AbstractClientRuntimeException;
import br.uff.labtempo.osiris.model.response.SensorResponse;
import br.uff.labtempo.osiris.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.uff.labtempo.osiris.util.AllowHeaderUtil.allows;

/**
 * Controller class that provide REST endpoints
 * to control and manage Sensors from SensorNet module
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/sensornet", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SensorController {
    private SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService){
        this.sensorService = sensorService;
    }

    /**
     * Get a mocked Sensor object created randomly at runtime.
     * For mock puroses only. Its not persisted.
     * @return SensorCoTo
     */
    @RequestMapping(value = "/sensors/mock", method = RequestMethod.GET)
    public ResponseEntity<?> getRandom() {
        return ResponseEntity.ok(this.sensorService.getRandom());
    }

    /**
     * Get a list of all available HTTP methods of the /sensors/mock endpoint
     * @return list of HTTP methods
     */
    @RequestMapping(value = "/sensors/mock", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsMock() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get all available Sensors from SensorNet module
     * @return List of SensorResponse
     */
    @RequestMapping(value = "/sensors", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<SensorResponse> sensorResponseList = null;
        try {
            sensorResponseList = this.sensorService.getAll();
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(sensorResponseList == null || sensorResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sensorResponseList);
    }

    /**
     * Get a list of all available HTTP methods of the /sensors endpoint
     * @return List of HTTP methods
     */
    @RequestMapping(value = "/sensors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsSensors() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get all Sensors from SensorNet based on a specific Network
     * @param networkId
     * @return List of SensorResponses (SensorSnTo from a {networkId} Network)
     */
    @RequestMapping(value = "/networks/{networkId}/sensors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByNetworkId(@PathVariable String networkId) {
        List<SensorResponse> sensorResponseList;
        try {
            sensorResponseList = this.sensorService.getAllByNetworkId(networkId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(sensorResponseList == null || sensorResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sensorResponseList);
    }

    /**
     * Get a list of all available HTTP methods of the /networks/{networkId}/sensors endpoint
     * @return list of HTTP methods
     */
    @RequestMapping(value = "/networks/{networkId}/sensors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdSensors() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get all Sensors from SensorNet module basend on specific Network id and Collector id
     * @param networkId
     * @param collectorId
     * @return List of Sensors from {collectorId} Collector from {networkId} Network
     */
    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}/sensors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByCollectorIdAndNetworkId(@PathVariable String networkId, @PathVariable String collectorId) {
        List<SensorResponse> sensorResponseList;
        try {
            sensorResponseList = this.sensorService.getAllByCollectorIdAndNetworkId(networkId, collectorId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(sensorResponseList == null || sensorResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sensorResponseList);
    }

    /**
     * Get a list of all available HTTP methods of the /networks/{networkId}/collectors/{collectorId}/sensors endpoint
     * @return list of HTTP methods
     */
    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}/sensors", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdCollectorIdSensors() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }

    /**
     * Get a specific Sensor from SensorNet module, based on a unique sensorId, networkid and collectorId
     * @param networkId
     * @param collectorId
     * @param sensorId
     * @return Sensor with {sensorId}, from {collectorId} Collector from {networkId} Network.
     */
    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}/sensors/{sensorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getByCollectorIdAndNetworkId(@PathVariable String networkId, @PathVariable String collectorId, @PathVariable String sensorId) {
        SensorResponse sensorResponse;
        try {
            sensorResponse = this.sensorService.getByCollectorIdAndNetworkId(networkId, collectorId, sensorId);
        } catch (AbstractRequestException e) {
            return ResponseEntity.status(e.getStatusCode().toCode()).build();
        } catch (AbstractClientRuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if(sensorResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sensorResponse);
    }

    /**
     * Get a list of all available HTTP methods of the /networks/{networkId}/collectors/{collectorId}/sensors/{sensorId} endpoint
     * @return List of HTTP methods
     */
    @RequestMapping(value = "/networks/{networkId}/collectors/{collectorId}/sensors/{sensorId}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsNetworkIdCollectorIdSensorId() {
        return allows(HttpMethod.GET, HttpMethod.OPTIONS);
    }
}
