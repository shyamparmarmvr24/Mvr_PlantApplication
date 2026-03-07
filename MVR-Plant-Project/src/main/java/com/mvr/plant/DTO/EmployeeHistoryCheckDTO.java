package com.mvr.plant.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeHistoryCheckDTO {

    private boolean exists;

    private List<EmployeeHistoryItem> historyList;

}
