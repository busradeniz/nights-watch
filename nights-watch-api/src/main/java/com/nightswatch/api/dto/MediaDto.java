package com.nightswatch.api.dto;

public class MediaDto implements EntityDto {

    private Long id;
    private String fileName;
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaDto mediaDto = (MediaDto) o;

        if (id != null ? !id.equals(mediaDto.id) : mediaDto.id != null) return false;
        if (fileName != null ? !fileName.equals(mediaDto.fileName) : mediaDto.fileName != null) return false;
        return !(url != null ? !url.equals(mediaDto.url) : mediaDto.url != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
