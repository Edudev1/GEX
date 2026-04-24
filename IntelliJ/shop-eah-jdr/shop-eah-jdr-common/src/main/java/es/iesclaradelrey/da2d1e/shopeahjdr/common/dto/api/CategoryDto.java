package es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.api;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private String image;
}
