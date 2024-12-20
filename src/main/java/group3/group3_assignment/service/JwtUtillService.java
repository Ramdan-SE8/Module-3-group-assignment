package group3.group3_assignment.service;

import java.util.Map;

public interface JwtUtillService {

    Map<String, String> generateToken(String username, String password);

    Map<String, String> validateToken(String Token);

}
