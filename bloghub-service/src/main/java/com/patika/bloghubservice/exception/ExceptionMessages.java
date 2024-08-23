package com.patika.bloghubservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String USER_NOT_FOUND = "kullanıcı bulunamadı";
    public static final String USER_ALREADY_DEFINED = "bu email ile kayıtlı kullanıcı bulundu";
    public static final String USER_EMAIL_CAN_NOT_BE_EMPTY = "email alanı boş olamaz";
    public static final String CURRENT_PASSWORD_DOES_NOT_MATCH = "eski şifrenizi hatalı girdiniz.";
    public static final String MAXIMUM_LIKE_LIMIT_REACHED = "bu blog için   beğeni sayısı 50'den fazla olamaz";
    public static final String UNVERIFIED_EMAIL = "mail doğrulanmadı";

}
