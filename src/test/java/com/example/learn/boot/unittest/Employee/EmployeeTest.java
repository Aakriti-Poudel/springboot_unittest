package com.example.learn.boot.unittest.Employee;

import com.example.learn.boot.unittest.UnitTestApplicationTests;
import com.example.learn.boot.unittest.model.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class EmployeeTest extends UnitTestApplicationTests {
    private EmployeeTest Id;

    @Test
    public void createEmployeeTest() throws Exception {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setName("Employee");
        dto.setAddress("Kathmandu");
        dto.setEmail("user@user.com");
        dto.setPhone("9876554433");

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Employee"))
                .andExpect(jsonPath("$.address").value("Kathmandu"))
                .andExpect(jsonPath("$.email").value("user@user.com"))
                .andExpect(jsonPath("$.phone").value("9876554433"));
    }

    @Test
    public void createEmployeeTestNeg() throws Exception {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setName(null);
        dto.setAddress("Kathmandu");
        dto.setEmail("user@user.com");
        dto.setPhone("9876554433");

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isBadRequest());

    }




    @Test
    public void getEmployeeById() throws Exception {
        createEmployee("sami", "pkr", "sami@gmail.com", "67687888");


        mockMvc.perform(get("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("sami"));

    }

    @Test
    public void getEmployeeByIdNotFound() throws Exception {
        createEmployee("sami", "pkr", "sami@gmail.com", "67687888");

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void listAllTest() throws Exception {
        createEmployee("subu", "htd", "subu@gmail.com", "987654321");

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("subu"));
    }

    @Test
    public void listAllTestNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testUpdateEmployee() throws Exception {
        EmployeeDTO dto = getCreatedEmployee("sita", "pkr", "sit@gmail.com", "987");
        EmployeeDTO employeeDTO=random.nextObject(EmployeeDTO.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/employee/"+dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(employeeDTO.getName())))
                .andExpect(jsonPath("$.address", is(employeeDTO.getAddress())))
                .andExpect(jsonPath("$.email", is(employeeDTO.getEmail())))
                .andExpect(jsonPath("$.phone", is(employeeDTO.getPhone())));
    }

    @Test
    public void testUpdateEmployeeBadRequest() throws Exception {
        EmployeeDTO dto = getCreatedEmployee("sita", "pkr", "sit@gmail.com", "987");
        EmployeeDTO employeeDTO = random.nextObject(EmployeeDTO.class);
        employeeDTO.setName(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/employee/"+dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEmployeeNot() throws Exception {
        createEmployee("sita", "pkr", "sit@gmail.com", "987");
        EmployeeDTO employeeDTO=random.nextObject(EmployeeDTO.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk());

    }

    @Test
    public void delete() throws Exception {
        createEmployee("subu", "htd", "subu@gmail.com", "987654321");
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void deleteNotFound() throws Exception {
        createEmployee("subu", "htd", "subu@gmail.com", "987654321");
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
