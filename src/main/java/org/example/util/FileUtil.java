package org.example.util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static List<String> createdImagesNames = new ArrayList<>();
    private static List<ImageIcon> createdIcons = new ArrayList<>();

    public static ImageIcon create(String imagePath) {
        int imageIndex = createdImagesNames.indexOf(imagePath);
        if (imageIndex >= 0) {
            return createdIcons.get(imageIndex);
        } else {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            createdImagesNames.add(imagePath);
            createdIcons.add(imageIcon);
            return imageIcon;
        }
    }

    public static String getImagePath() {
        return "src/main/java/org/example/icon/";
    }

    public static String createImagePath(String image) {
        return getImagePath() + image + ".png";
    }

    public static int[][] gerarRoadMesh(File arquivo) throws Exception {
        List<String> linhasArquivo = Files.readAllLines(Path.of(arquivo.getPath()));

        String[] linhasMalhaString = (linhasArquivo.get(0)).split("\t");
        int linhasMalha = Integer.parseInt(linhasMalhaString[0].trim());
        String[] linhasArquivoString = (linhasArquivo.get(1)).split("\t");
        int colunasMalha = Integer.parseInt(linhasArquivoString[0].trim());

        int[][] malhaRodoviaria = new int[linhasMalha][colunasMalha];

        linhasArquivo = linhasArquivo.subList(2, linhasArquivo.size());

        for (int linha = 0; linha < linhasArquivo.size(); linha++) {
            String[] colunas = linhasArquivo.get(linha).split("\t");
            for (int coluna = 0; coluna < colunas.length; coluna++) {
                malhaRodoviaria[linha][coluna] = Integer.parseInt(colunas[coluna]);
            }
        }

        return malhaRodoviaria;
    }

    private static class ResizedImageIcon {

        private ImageIcon imageIcon;
        private String imagePath;
        private int width;
        private int height;

        public ResizedImageIcon(String imagePath, int width, int height) {
            init(imagePath, width, height);
        }

        private void init(String imagePath, int width, int height) {
            this.imagePath = imagePath;
            this.width = width;
            this.height = height;
        }

        public ImageIcon getImageIcon() {
            return imageIcon;
        }

        public void setImageIcon(ImageIcon imageIcon) {
            this.imageIcon = imageIcon;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + height;
            result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
            result = prime * result + width;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ResizedImageIcon other = (ResizedImageIcon) obj;
            if (height != other.height)
                return false;
            if (imagePath == null) {
                if (other.imagePath != null)
                    return false;
            } else if (!imagePath.equals(other.imagePath))
                return false;
            if (width != other.width)
                return false;
            return true;
        }

    }

    private static List<ResizedImageIcon> resizedImageIconsCreated = new ArrayList<>();

    public static ImageIcon create(String imagePath, int width, int height) {
        ResizedImageIcon resized = new ResizedImageIcon(imagePath, width, height);
        int resizedIndex = resizedImageIconsCreated.indexOf(resized);
        if (resizedIndex >= 0) {
            return resizedImageIconsCreated.get(resizedIndex).getImageIcon();
        } else {
            ImageIcon imageIcon = createImageIcon(imagePath, width, height);
            resized.setImageIcon(imageIcon);
            resizedImageIconsCreated.add(resized);
            return imageIcon;
        }
    }

    private static ImageIcon createImageIcon(String imagePath, int width, int height) {
        ImageIcon imageIcon = create(imagePath);
        if (imageIcon != null && width > 0 && height > 0) {
            Image imageResized = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon imageIconResized = new ImageIcon(imageResized);
            return imageIconResized;
        } else {
            return null;
        }
    }
}
