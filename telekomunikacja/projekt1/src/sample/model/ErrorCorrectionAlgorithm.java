package sample.model;

public class ErrorCorrectionAlgorithm {

    BitArray[] hMatrix = new BitArray[8];

    public ErrorCorrectionAlgorithm() {
        hMatrix[0] = BitArray.bitStringToBitArray("0111111110000000");
        hMatrix[1] = BitArray.bitStringToBitArray("1011111101000000");
        hMatrix[2] = BitArray.bitStringToBitArray("1101111100100000");
        hMatrix[3] = BitArray.bitStringToBitArray("1110111100010000");
        hMatrix[4] = BitArray.bitStringToBitArray("1111011100001000");
        hMatrix[5] = BitArray.bitStringToBitArray("1111101100000100");
        hMatrix[6] = BitArray.bitStringToBitArray("1111110100000010");
        hMatrix[7] = BitArray.bitStringToBitArray("1111111000000001");
    }

    public BitArray addParityBits(BitArray array) {
        BitArray finalArray = new BitArray(16);
        for(int i = 0; i < 8 ; i++) {
            finalArray.setBit(array.getBit(i), i);
            if(hMatrix[i].divide(8,8).OR(array).isParity()) {
                finalArray.setBit(0, 8+i);
            } else {
                finalArray.setBit(1, 8+i);
            }
        }
        return finalArray;
    }

    public void checkCorrection(BitArray array) {

    }

    public BitArray correctSingleError(BitArray array) {
        BitArray finalArray = new BitArray(8);



        return finalArray;
    }

    public BitArray correctDoubleError(BitArray array) {
        BitArray finalArray = new BitArray(8);



        return finalArray;
    }

    private BitArray multiplicateMatrix(BitArray array) {
        BitArray finalArray = new BitArray(8);

        for(int i = 0; i < 8; i++) {
          //  finalArray.setBit( , i);
        }

        return finalArray;
    }

}
