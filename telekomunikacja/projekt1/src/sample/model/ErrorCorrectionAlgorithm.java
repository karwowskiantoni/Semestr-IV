package sample.model;

public class ErrorCorrectionAlgorithm {

    BitArray[] hMatrix = new BitArray[8];

    public ErrorCorrectionAlgorithm() {

        //implementacja macierzy dodająca 8 bitów parzystości według wzoru
        hMatrix[0] = BitArray.bitStringToBitArray("0111111010000000");
        hMatrix[1] = BitArray.bitStringToBitArray("0011111101000000");
        hMatrix[2] = BitArray.bitStringToBitArray("1001111100100000");
        hMatrix[3] = BitArray.bitStringToBitArray("1100111100010000");
        hMatrix[4] = BitArray.bitStringToBitArray("1110011100001000");
        hMatrix[5] = BitArray.bitStringToBitArray("1111001100000100");
        hMatrix[6] = BitArray.bitStringToBitArray("1111100100000010");
        hMatrix[7] = BitArray.bitStringToBitArray("1111110000000001");
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

    //usuwa bity parzystości, konwertuje na postać ciągu znaków
    public String removeParityBits(String bitData) {
        String data = BitArray.bitStringToBitArray(bitData).bitArrayToString();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i = i + 2){
            result.append(data.charAt(i));
        }
        return result.toString();
    }

    //poprawia do dwóch błędów w ciągu bitów
    public String correct(String bitData) {
        BitArray array = BitArray.bitStringToBitArray(bitData);
        BitArray finalArray = new BitArray(0);
        int arrayFirstSize = array.length/16;
        BitArray.xorBitArrayItself(array);
        for(int i = 0; i < arrayFirstSize; i++) {
            System.out.println("blok numer " + i);
            finalArray = finalArray.connect(correctSingleBlock(array.getBitsFromLeftSide(16)));
            array = array.getBitsFromRightSide(16);
        }

        return finalArray.bitArrayToBitString();
    }













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
    private BitArray correctSingleBlock(BitArray block) {
        BitArray[] transposedHMatrix = transposeMatrix(hMatrix);
            for(int i = 0; i < transposedHMatrix.length; i++) {
                if (transposedHMatrix[i].isEqual(singleBlockOutput(block))) {
                    System.out.println("błędny bit numer " + i);
                    block.negateSingleBit(i);
                }
            }
        return block;
    }

}
