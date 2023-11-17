package com.ashwinbhatt.craft.mockkafka;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@RestController
public class Controller {

    public static Map<String, BlockingDeque<Object>> blockingQueueMap = new HashMap<>();

    @PostMapping("/kafka/{topic}")
    public ResponseEntity<Boolean> addToQueue(@RequestBody Object data,@PathVariable String topic) {
        if(data == null) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        }
        if(topic == null) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        }
        synchronized (this) {
            if(!blockingQueueMap.containsKey(topic)) {
                blockingQueueMap.put(topic, new LinkedBlockingDeque<>());
            }
            BlockingDeque<Object> blockingDeque = blockingQueueMap.get(topic);
            blockingDeque.add(data);
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/kafka/{topic}")
    public ResponseEntity<Object> getFromQueue(@PathVariable String topic) {
        if(topic == null) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        }
        synchronized (this) {
            if(!blockingQueueMap.containsKey(topic)) {
                return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
            }
            BlockingDeque<Object> blockingDeque = blockingQueueMap.get(topic);
            if(blockingDeque.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.IM_USED);
            }
            return new ResponseEntity<>(blockingDeque.pop(), HttpStatus.OK);
        }
    }
}
