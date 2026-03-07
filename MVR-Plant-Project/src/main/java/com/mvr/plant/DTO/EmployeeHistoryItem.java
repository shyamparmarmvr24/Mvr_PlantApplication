package com.mvr.plant.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeHistoryItem {

    private String employeeId;
    private String employeeName;
    private String designation;
    private Long mobileNo;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate dateOfJoining;
    private LocalDate dateOfLeaving;
}
