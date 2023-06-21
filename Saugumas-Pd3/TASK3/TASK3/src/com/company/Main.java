package com.company;

import java.math.*;
import java.util.*;

        // Pirminių skaičių sąrašas: http://compoasso.free.fr/primelistweb/page/prime/liste_online_en.php

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private int p, q, n, z, d = 0, e, i;


    // p ir q -> PIRMINIAI SKAIČIAI
    // n       -> n = p * q    (modulis)
    // d       -> PRIVATUSIS RAKTAS
    // z (fi)  -> z = (p - 1)*(q - 1) (kiek yra tarpusavio pirminių skaičių)

    // e       -> VIEŠASIS RAKTAS (1-fi) (negali būti mažesnis nei 1 ir didesnis nei fi)
    // i       -> ciklo kintamasis

    public static void main(String args[]) {
        run();
    }

    private Main() {
        System.out.println("Įveskite PIRMINĮ skaičių p: ");
        p = scanner.nextInt();
        System.out.println("Įveskite PIRMINĮ skaičių q: ");
        q = scanner.nextInt();

        n = p * q;
        z = (p - 1) * (q - 1);
        System.out.println("z reikšmė = (p - 1) * (q - 1)  = " + z);

        for (e = 2; e < z; e++) {
            if (gcd(e, z) == 1) {
                break;
            }
        }
        System.out.println("e reikšmė (viešasis raktas) = " + e);

        for (i = 0; i <= 9; i++) {
            int x = 1 + (i * z);
            if (x % e == 0) {
                d = x / e;
                break;
            }
        }
        System.out.println("Privatusis raktas (d) yra = " + d);
    }

    private static int gcd(int e, int z) {
        if (e == 0) {
            return z;
        } else {
            return gcd(z % e, e);
        }
    }

    private double encrypt(int message) {
        return (Math.pow(message, e)) % n;
    }

    private double[] encrypt(String message) {
        int[] charactersAsNumbers = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            charactersAsNumbers[i] = message.codePointAt(i);
        }
        System.out.println("Pradinis tekstas kaip skaičių seka: " + Arrays.toString(charactersAsNumbers));

        double[] encryptedMessage = new double[message.length()];
        for (int i = 0; i < charactersAsNumbers.length; i++) {
            encryptedMessage[i] = encrypt(charactersAsNumbers[i]);
        }
        return encryptedMessage;
    }

    private BigInteger decrypt(double encrypted) {
        BigInteger N = BigInteger.valueOf(n);
        BigInteger C = BigDecimal.valueOf(encrypted).toBigInteger();
        return (C.pow(d)).mod(N);
    }

    private String decrypt(double[] encrypted) {
        StringBuilder builder = new StringBuilder();
        for (double encryptedCharacter : encrypted) {
            BigInteger decryptedCharacter = decrypt(encryptedCharacter);
            builder.append(Character.toChars(decryptedCharacter.intValue()));
        }
        return builder.toString();
    }

    private static void run() {
        System.out.println("Sveiki, įveskite žinutę, kurią norite užšifruoti ir atšifruoti ");
        String message = scanner.nextLine();
        Main main = new Main();

        double[] c = main.encrypt(message);
        System.out.println("Užšifruota žinutė: " + Arrays.toString(c));

        String messageDecrypted = main.decrypt(c);
        System.out.println("Atšifruota žinutė: " + messageDecrypted);
    }
}