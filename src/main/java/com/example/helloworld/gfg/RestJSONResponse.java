package com.example.helloworld.gfg;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
@RestController // It is combine the @Controller and @ResponseBody to convert the responses into XML/JSON format
@RequestMapping(path = "/JSON", produces = "application/json")
public class RestJSONResponse {

    @GetMapping("/data")
    public ArrayList<DomainBean> get() {

        ArrayList<DomainBean> arr = new ArrayList<>();

        DomainBean userOne = new DomainBean();
        userOne.setId("1");
        userOne.setName("@geek");
        userOne.setData("GeeksforGeeks");

        DomainBean userTwo = new DomainBean();
        userTwo.setId("2");
        userTwo.setName("@drash");
        userTwo.setData("Darshan.G.Pawar");

        arr.add(userOne);
        arr.add(userTwo);

        return arr;
    }
    @GetMapping("/{id}/{name}/{data}")
    public ResponseEntity<DomainBean> getData(@PathVariable("id") String id,
                                              @PathVariable("name") String name,
                                              @PathVariable("data") String data) {

        DomainBean user = new DomainBean();
        user.setId(id);
        user.setName(name);
        user.setData(data);

        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<DomainBean> entity = new ResponseEntity<>(user, headers, HttpStatus.CREATED);

        return entity;
    }
}
