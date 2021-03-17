package sample.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ErrorCorrectionAlgorithm {

    BitArray[] hMatrix = new BitArray[8];

    public ErrorCorrectionAlgorithm() {

        //implementacja macierzy dodająca 8 bitów parzystości według wzoru
        hMatrix[0] = BitArray.bitStringToBitArray("01011111 10000000");
        hMatrix[1] = BitArray.bitStringToBitArray("10101111 01000000");
        hMatrix[2] = BitArray.bitStringToBitArray("11010111 00100000");
        hMatrix[3] = BitArray.bitStringToBitArray("11101011 00010000");
        hMatrix[4] = BitArray.bitStringToBitArray("11110101 00001000");
        hMatrix[5] = BitArray.bitStringToBitArray("11111010 00000100");
        hMatrix[6] = BitArray.bitStringToBitArray("01111101 00000010");
        hMatrix[7] = BitArray.bitStringToBitArray("10111110 00000001");
    }


    //konwertuje na postać ciągu bitów i dodaje bity parzystości
    public String encode(String data) {
        BitArray finalData = new BitArray(0);
        for(int i = 0; i < data.length(); i++) {
             finalData = finalData.connect(addParityBits(BitArray.stringToBitArray(data.substring(i, i+1))));
        }
        return finalData.bitArrayToBitString();
    }

    //sprawdza poprawność ciągu bitów na podstawie macierzy
    public boolean checkCorrection(String bitData) {
        BitArray array = BitArray.bitStringToBitArray(bitData);
        if (array.length % 16 != 0) {
            return false;
        }
        int arrayFirstSize = array.length/16;
        for(int i = 0; i < arrayFirstSize; i++) {

            if (!singleBlockOutput(array.getBitsFromLeftSide(16)).isZero()) {
                return false;
            }
            array = array.getBitsFromRightSide(16);
        }

        return true;
    }

    //poprawia do dwóch błędów w ciągu bitów
    public String correct(String bitData) {
        BitArray array = BitArray.bitStringToBitArray(bitData);
        BitArray finalArray = new BitArray(0);
        int arrayFirstSize = array.length/16;
        for(int i = 0; i < arrayFirstSize; i++) {
            finalArray = finalArray.connect(correctSingleBlock(array.getBitsFromLeftSide(16), i));
            array = array.getBitsFromRightSide(16);
        }

        return finalArray.bitArrayToBitString();
    }

    //usuwa bity parzystości, konwertuje na postać ciągu znaków
    public String removeParityBits(String bitData) {
        bitData = bitData.replace(" ", "");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bitData.length(); i = i + 16){
            result.append(bitData, i, i+8);
        }
        return BitArray.bitStringToBitArray(result.toString()).bitArrayToString();
    }

    //-------------------------------------------------------------------------------------------------------

    //funkcja dodająca 8 bitów parzystości do 1 bajtowego bloku danych, wyjściowy blok ma 2 bajty
    private BitArray addParityBits(BitArray array) {
        BitArray finalArray = new BitArray(16);
        for(int i = 0; i < 8 ; i++) {
            finalArray.setBit(array.getBit(i), i);
            if(hMatrix[i].getBitsFromLeftSide(8).AND(array).isParity()) {
                finalArray.setBit(0, 8+i);
            } else {
                finalArray.setBit(1, 8+i);
            }
        }
        return finalArray;
    }

    // funkcja zwracająca wektor błędu dla 2 bajtowego bloku (8 bitów danych + 8 bitów parzystości)
    private BitArray singleBlockOutput(BitArray array) {
        BitArray finalArray = new BitArray(8);
        for(int i = 0; i < 8; i++) {
          finalArray.setBit(hMatrix[i].AND(array).recursiveXOR(), i);
        }
        return finalArray;
    }

    //funkcja transponuje dowolną macierz zbudowaną na BitArray
    private BitArray[] transposeMatrix(BitArray[] matrix) {
        BitArray[] transposedMatrix = new BitArray[matrix[0].length];

        for(int i = 0; i < transposedMatrix.length; i++) { // i = (0, 15)
            transposedMatrix[i] = new BitArray(matrix.length);
            for(int j = 0; j < matrix.length; j++) { // j = (0, 7)
                transposedMatrix[i].setBit(matrix[j].getBit(i), j);
            }
        }
        return transposedMatrix;
    }

    //funkcja poprawia do dwóch błędów w jednym 2 bajtowym bloku kodu
    private BitArray correctSingleBlock(BitArray block, int blockNumber) {
        BitArray[] transposedHMatrix = transposeMatrix(hMatrix);

        // poprawa dwóch błędów
        HashMap<BitArray, Integer[]> allXORedColumns = xorBitArrays(transposedHMatrix);
        Set<Map.Entry<BitArray, Integer[]>> allXORedColumnsSet= allXORedColumns.entrySet();
        for(Map.Entry<BitArray, Integer[]> entry: allXORedColumnsSet) {
            if(entry.getKey().isEqual(singleBlockOutput(block))) {
                Integer[] coordinates = entry.getValue();
                block.negateSingleBit(coordinates[0]);
                block.negateSingleBit(coordinates[1]);
                System.out.print("błędne bity numer " + coordinates[0]);
                System.out.print(" oraz " + coordinates[1]);
                System.out.println(" w bloku numer " + blockNumber);
                return block;
            }
        }

        // poprawa jednego błędu
        for(int i = 0; i < transposedHMatrix.length; i++) {
            if (transposedHMatrix[i].isEqual(singleBlockOutput(block))) {
                block.negateSingleBit(i);
                System.out.println("błędny bit numer " + i + " w bloku numer " + blockNumber);
                return block;
            }
        }
        return block;
    }

    // zwraca wszystkie kombinacje xorów tablicy ciągów bitów
    public HashMap<BitArray, Integer[]> xorBitArrays(BitArray[] data) {
        HashMap<BitArray, Integer[]> xoredFinalArray = new HashMap<>();
        for (int baseBitArrayElement = 0; baseBitArrayElement < data.length - 1; baseBitArrayElement++) {
            for (int xoringBitArrayElement = baseBitArrayElement + 1; xoringBitArrayElement < data.length; xoringBitArrayElement++) {
                xoredFinalArray.put(data[baseBitArrayElement].XOR(data[xoringBitArrayElement]), new Integer[]{baseBitArrayElement, xoringBitArrayElement});
            }
        }
        return xoredFinalArray;
    }
}
