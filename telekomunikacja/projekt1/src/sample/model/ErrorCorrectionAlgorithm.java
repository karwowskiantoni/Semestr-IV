package sample.model;

public class ErrorCorrectionAlgorithm {

    BitArray[] hMatrix = new BitArray[8];

    public ErrorCorrectionAlgorithm() {
        hMatrix[0] = BitArray.bitStringToBitArray("0111111010000000");
        hMatrix[1] = BitArray.bitStringToBitArray("0011111101000000");
        hMatrix[2] = BitArray.bitStringToBitArray("1001111100100000");
        hMatrix[3] = BitArray.bitStringToBitArray("1100111100010000");
        hMatrix[4] = BitArray.bitStringToBitArray("1110011100001000");
        hMatrix[5] = BitArray.bitStringToBitArray("1111001100000100");
        hMatrix[6] = BitArray.bitStringToBitArray("1111100100000010");
        hMatrix[7] = BitArray.bitStringToBitArray("1111110000000001");
    }

    public BitArray addParityBits(BitArray array) {
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
    private BitArray multiplicateMatrix(BitArray array) {
        BitArray finalArray = new BitArray(8);

        for(int i = 0; i < 8; i++) {
          finalArray.setBit(hMatrix[i].AND(array).recursiveXOR(), i);
        }

        return finalArray;
    }

}
