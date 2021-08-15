package com.ece.nsu.spring2021.cse499.arschoolbook.utils;

/**
 * Util class for holding static values of,
 * figure id, names of images in the ARImageDatabase and .glb file names in assets
 */
public abstract class ArUtil {

    public static final int
            SOLAR_SYSTEM_IMG_ID = 0,
            VIRUS_IMG_ID = 1,
            AMOEBA_IMG_ID = 2,
            ENTAMOEBA_IMG_ID = 3,
            PLANT_CELL_IMG_ID = 4,
            ANIMAL_CELL_IMG_ID = 5,
            NUCLEUS_IMG_ID = 6,
            NEURON_IMG_ID = 7,
            LUNG_IMG_ID = 8,
            DIGESTIVE_SYSTEM_IMG_ID = 9;

    public static final String
            SOLAR_SYSTEM_IMG_NAME = "12_1.jpg",
            VIRUS_IMG_NAME = "1_1.jpg",
            AMOEBA_IMG_NAME = "1_6.jpg",
            ENTAMOEBA_IMG_NAME = "1_7.jpg",
            PLANT_CELL_IMG_NAME = "2_1_a.jpg",
            ANIMAL_CELL_IMG_NAME = "2_1_b.jpg",
            NUCLEUS_IMG_NAME = "2_3.jpg",
            NEURON_IMG_NAME = "2_6.jpg",
            LUNG_IMG_NAME = "4_3.jpg",
            DIGESTIVE_SYSTEM_IMG_NAME = "5_1.jpg";

    // name of the models saved inside assets folder
    public static final String
            SOLAR_SYSTEM_MODEL_FILE = "solarSystem.glb",
            VIRUS_MODEL_FILE = "virus.glb",
            //AMOEBA_MODEL_FILE = "1_6.jpg",
            //ENTAMOEBA_MODEL_FILE = "1_7.jpg",
            PLANT_CELL_MODEL_FILE = "plantCell.glb",
            ANIMAL_CELL_MODEL_FILE = "animalCell.glb",
            //NUCLEUS_MODEL_FILE = "2_3.jpg",
            NEURON_MODEL_FILE = "neuron.glb",
            LUNG_MODEL_FILE = "lung.glb",
            DIGESTIVE_MODEL_FILE = "digestiveSystem.glb";

}
