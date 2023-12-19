package edu.project4.image;

public enum ImageFormat {

    PNG("png");

    private final String formatName;

    ImageFormat(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatName() {
        return formatName;
    }

}
