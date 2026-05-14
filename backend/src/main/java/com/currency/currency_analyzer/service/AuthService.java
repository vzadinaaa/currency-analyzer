package com.currency.currency_analyzer.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;

@Service
public class AuthService {

    public boolean login(

            String username,

            String password
    ) {

        try {

            BufferedReader reader =

                    new BufferedReader(
                            new FileReader(
                                    "users.txt"
                            )
                    );

            String line;

            String hashedPassword =
                    hashPassword(password);

            while (
                    (line = reader.readLine())
                    != null
            ) {

                String[] parts =
                        line.split(":");

                if (
                        parts.length == 2
                ) {

                    String storedUsername =
                            parts[0];

                    String storedPassword =
                            parts[1];

                    if (

                            storedUsername.equals(
                                    username
                            )

                            &&

                            storedPassword.equals(
                                    hashedPassword
                            )
                    ) {

                        reader.close();

                        return true;
                    }
                }
            }

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    String hashPassword(
            String password
    ) {

        try {

            MessageDigest md =

                    MessageDigest.getInstance(
                            "SHA-256"
                    );

            byte[] hash =
                    md.digest(
                            password.getBytes()
                    );

            StringBuilder sb =
                    new StringBuilder();

            for (byte b : hash) {

                sb.append(
                        String.format(
                                "%02x",
                                b
                        )
                );
            }

            return sb.toString();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}