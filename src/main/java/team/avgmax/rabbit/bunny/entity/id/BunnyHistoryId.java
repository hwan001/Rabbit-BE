package team.avgmax.rabbit.bunny.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BunnyHistoryId implements Serializable {
    private LocalDate date;
    private String bunnyId;
}
