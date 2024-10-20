package com.anuradha.annexifymobilebff.dto;

import java.util.List;

public record PostImageSaveDto(
        String id,
        String thumbnail,
        List<String> images
) {
}
