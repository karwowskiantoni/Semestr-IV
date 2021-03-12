package sample.model;

import java.util.ArrayList;

public class BitArray {
    byte[] bytes;
    int length;

    public BitArray(int length) {
        this.length = length;
        this.bytes = new byte[(int) Math.ceil(length/8.0)];
    }

    public BitArray(byte[] bytes){
        this.bytes = bytes;
        length = bytes.length*8;
    }

    public int getLength(){
        return length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBit(int value, int index) {

        byte chosenByte = bytes[(index) / 8];

        if(value == 0) {
            chosenByte = (byte) (chosenByte & ~(1 << (8 - ((index +1) - (index / 8) * 8))));
        }else {
            chosenByte = (byte) (    (byte) (value << (8 - ((index +1) - (index / 8) * 8)))     |    chosenByte    );
        }

        bytes[index / 8] = chosenByte;
    }

    public byte getBit(int index) {
        byte chosenByte = bytes[index / 8];
        //f(x)----------------//
        return (byte) (chosenByte >>(8 - ((index + 1) - (index / 8) * 8))  & 1);
        //g(f(x)---------------------//

        /*
             1 2 3 4 5 6 7 8 9 10 11 ... x
             1 2 3 4 5 6 7 8 1 2  3 ...  f(x)
             7 6 5 4 3 2 1 0 7 6  5 ... g(f(x)) = 8 - f(x)
        */
    }

    public byte getByte(int index){
        byte result = bytes[index];
        if(index == bytes.length-1){   // length%8   1-1  2-3 3-7 4-15 5-31 6-63 7-127
            result = (byte) ((result >>> (8-length%8)) & 3);
        }
        return result;
    }

    static public BitArray bitStringToBitArray(String data){
        data = data.replace(" ", "");
        BitArray array = new BitArray(data.length());
        for (int i = 0; i < array.getLength(); i++) {
            if(data.split("")[i].equals("1")){
                array.setBit(1,i);
            }if(data.split("")[i].equals("0")) {
                array.setBit(0, i);
            }
        }
        return array;
    }

    public String bitArrayToBitString() {
        String result = "";

        for(int i = 0; i < this.getBytes().length*8; i++){
            if(i%8 == 0 && i != 0){
                result += " ";
            }
            if(this.getBit(i) == 2){
                result += "2";
            }
            if(this.getBit(i) == 1){
                result += "1";
            }
            if(this.getBit(i) == 0){
                result += "0";
            }
        }
        return result;
    }

    static public BitArray stringToBitArray(String data) {
        return new BitArray(data.getBytes());
    }

    public String bitArrayToString() {
        return new String(bytes);
    }

    public String bitArrayToHexString(){
        byte[] fourtimesbits = new byte[bytes.length*2];
        String result ="";
        for(int i = 0; i < bytes.length; i++){
            fourtimesbits[i*2] =(byte) ((bytes[i] >>> 4) & 15) ;
            fourtimesbits[i*2+1] = (byte) (bytes[i] & 15);

        }
        for(byte b: fourtimesbits) {
            result += Integer.toHexString(b);
        }

        return result;
    }

    static public BitArray hexStringToBitArray(String data){
        byte[] dataInBytes = new byte[data.length()];
        for(int i = 0 ; i < data.length(); i++){
            String pom = "" + data.toCharArray()[i];
            dataInBytes[i] = (byte) Integer.parseInt(pom, 16);
        }
        byte[] dataInFourTimesBits = new byte[data.length()/2];
        for(int i = 0; i < dataInFourTimesBits.length; i++){
            dataInFourTimesBits[i] = (byte) (dataInBytes[i*2]*16 + dataInBytes[i*2+1]);
        }
        return new BitArray(dataInFourTimesBits);
    }

    public BitArray getBitsFromLeftSide(int index) {
        BitArray leftArray = new BitArray(index);

        for (int i = 0; i < index; i++) {
            leftArray.setBit(this.getBit(i), i);
        }

        return leftArray;
    }

    public BitArray getBitsFromRightSide(int index) {
        BitArray rightArray = new BitArray(length - index);

        for (int i = 0; i < length - index; i++) {
            rightArray.setBit(this.getBit(index + i), i);
        }

        return rightArray;
    }

    public BitArray XOR(BitArray bitArray) {

        for(int i = 0; i < this.getBytes().length; i++){
            bitArray.getBytes()[i] = (byte) ((int)(this.getBytes()[i]) ^ (int)(bitArray.getBytes()[i]));
        }

        return bitArray;
    }

    public boolean isParity() {
        int sum = 0;
        for(int i = 0 ; i < length ; i++) {
            if(this.getBit(i) == 1) {
                sum += 1;
            }
        }
        return sum % 2 == 0;
    }

    public BitArray connect(BitArray array) {
        BitArray result = new BitArray(array.getLength() + this.getLength());
        for(int i = 0; i < this.getLength(); i++){
            result.setBit(this.getBit(i), i);
        }

        for(int i = this.getLength(); i < result.getLength(); i++){
            result.setBit(array.getBit(i-this.getLength()) ,i);
        }
        return result;
    }

    public BitArray OR(BitArray array) {
        BitArray result = new BitArray(this.length);
        for(int i = 0; i < length; i++){
            if(array.getBit(i) == 1 || this.getBit(i) == 1) {
                result.setBit(1, i);
            } else {
                result.setBit(0, i);
            }
        }
        return result;
    }

    public BitArray AND(BitArray array){
        BitArray result = new BitArray(this.length);
        for(int i = 0; i < length; i++){
            if(array.getBit(i) == 1 && this.getBit(i) == 1) {
                result.setBit(1, i);
            } else {
                result.setBit(0, i);
            }
        }
        return result;
    }
    public int recursiveXOR() {
        int value = this.getBit(0);
        for(int i = 1; i < length; i++) {
            if(value == this.getBit(i)) {
                value = 0;
            }else {
                value = 1;
            }
        }
        return value;
    }

    public boolean isZero() {
        for(int i = 0; i < length; i++) {
            if(this.getBit(i) == 1){
                return false;
            }
        }
        return true;
    }

    public boolean isEqual(BitArray array) {
        if(array.length != this.length) {
            return false;
        }
        for(int i = 0; i < this.length; i++) {
            if(array.getBit(i) != this.getBit(i)) {
                return false;
            }
        }
        return true;
    }

    public void negateSingleBit(int index) {
        if(this.getBit(index) == 0) {
            this.setBit(1, index);
        }else {
            this.setBit(0, index);
        }
    }

    public static BitArray xorBitArrayItself(BitArray data){
        ArrayList<Integer> buffer = new ArrayList<>();
        int bitCounter = 0;
        int finalBitCounter = 0;

        for(int operativeByteNumber = 0; operativeByteNumber < data.length; operativeByteNumber = operativeByteNumber + 8){

            for(int xoringBit = operativeByteNumber + 8; xoringBit < data.length; xoringBit++){

                if(data.getBit(operativeByteNumber + bitCounter) == data.getBit(xoringBit)){
                    buffer.add(0);
                } else {
                    buffer.add(1);
                }
                finalBitCounter++;
                bitCounter++;
                if(bitCounter%8 == 0){
                    bitCounter = 0;
                }
            }
        }
        BitArray finalXorResult = new BitArray(buffer.size());
        for(int i = 0; i < buffer.size(); i++){
            finalXorResult.setBit(buffer.get(i), i);
        }
        System.out.println(finalXorResult.bitArrayToBitString());
        return finalXorResult;
    }

}
