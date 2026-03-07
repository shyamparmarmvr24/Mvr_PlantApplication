package com.mvr.plant.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWithOperationsDTO {

    private Long plantId;
    private String employeeId;
    private String employeeName;
    private String designation;
    private Long mobileNo;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate dateOfJoining;
    private LocalDate dateOfLeaving;

    private List<OperationDTO> operations;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OperationDTO {
        private Long operationId;
        private Boolean attendanceAm;
        private Boolean attendancePm;
        private LocalDate operationDate;
    }
}
