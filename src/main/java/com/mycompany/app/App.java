package com.mycompany.app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;


public class App {

    public class Entity {
        private String Fname;
        private String Lname;
        private String entity_id;
        
        public String getEntity_id() {
            return entity_id;
        }
        public void setEntity_id(String entity_id) {
            this.entity_id = entity_id;
        }
        public String getLname() {
            return Lname;
        }
        public void setLname(String lname) {
            Lname = lname;
        }
        public String getFname() {
            return Fname;
        }
        public void setFname(String fname) {
            Fname = fname;
        }
        
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("Entity ID "+ getEntity_id()+"\n");
            sb.append("First Name "+ getFname()+"\n");
            sb.append("Last name "+ getLname()+"\n");
            return sb.toString();
        }
        
    }
    
    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Semih CATAL 161101011 Bil481 Hw3");

        post("/compute", (req, res) -> {
            // System.out.println(req.queryParams("input1"));
            // System.out.println(req.queryParams("input2"));

            String input1 = req.queryParams("input1");
            java.util.Scanner sc1 = new java.util.Scanner(input1);
            sc1.useDelimiter("[;\r\n]+");
            java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
            while (sc1.hasNext()) {
                int value = Integer.parseInt(sc1.next().replaceAll("\\s", ""));
                inputList.add(value);
            }
            System.out.println(inputList);

            String input2 = req.queryParams("input2").replaceAll("\\s", "");
            int input2AsInt = Integer.parseInt(input2);

            boolean result = App.search(inputList, input2AsInt);

            Map map = new HashMap();
            map.put("result", result);
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());

        get("/compute", (rq, rs) -> {
            Map map = new HashMap();
            map.put("result", "not computed yet!");
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
    }
}
