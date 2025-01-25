// На основе класса BitSet разработайте программу для реализации битовых операций AND, OR, XOR, а также маскирования.


import java.util.BitSet;

public class BitSetOperations {

    // Метод для отображения содержимого BitSet
    private static void printBitSet(BitSet bitSet, String label) {
        System.out.println(label + ": " + bitSet);
    }

    public static void main(String[] args) {
        // Создаем два BitSet с размером 8
        BitSet bitSet1 = new BitSet(8);
        BitSet bitSet2 = new BitSet(8);

        bitSet1.set(0); // 00000001
        bitSet1.set(2); // 00000101
        bitSet1.set(5); // 00100101


        bitSet2.set(1); // 00000010
        bitSet2.set(2); // 00000110
        bitSet2.set(4); // 00010110

        // начальные значения
        printBitSet(bitSet1, "BitSet1");
        printBitSet(bitSet2, "BitSet2");

        //  AND (и)
        BitSet andResult = (BitSet) bitSet1.clone();
        andResult.and(bitSet2);
        printBitSet(andResult, "AND (BitSet1 и BitSet2)");

        //  OR (или)
        BitSet orResult = (BitSet) bitSet1.clone();
        orResult.or(bitSet2);
        printBitSet(orResult, "OR (BitSet1 или BitSet2)");

        //  XOR (исключающее или)
        BitSet xorResult = (BitSet) bitSet1.clone();
        xorResult.xor(bitSet2);
        printBitSet(xorResult, "XOR (BitSet1 xor BitSet2)");

        // маскирование (сохранение только тех битов, которые есть в маске)
        BitSet mask = new BitSet(8);
        mask.set(2); // Маска: 00000100
        mask.set(5); // Маска: 00100100

        BitSet maskedResult = (BitSet) bitSet1.clone();
        maskedResult.and(mask);
        printBitSet(maskedResult, "маскирование (BitSet1 и маска)");
    }
}
