package com.yoti.api.client.spi.remote;

import com.google.protobuf.ByteString;
import com.yoti.api.client.spi.remote.proto.DataEntryProto;
import com.yoti.api.client.spi.remote.proto.ExtraDataProto;
import com.yoti.api.client.spi.remote.proto.IssuingAttributesProto;
import com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto;

import java.util.Arrays;

public class TestAttributes {

    static final String THIRD_PARTY_ASSIGNED_ATTRIBUTE = "ChFjb20udGhpcmRwYXJ0eS5pZBIcdGVzdC10aGlyZC1wYXJ0eS1hdHRyaWJ1dG" +
            "UtMBgBIq8PCjdBTkMtRE9D79ePObVV+I+3DW6XEsoUWFNgdBMnngTTY0tHzCxLcI0eRdyavbDk1nyH9Qawzyh4Eo8IMIIECzCCAnOgAwIBA" +
            "gIRANBm827xWO4DtE54I1j/KVIwDQYJKoZIhvcNAQELBQAwJzElMCMGA1UEAxMcdGhpcmQtcGFydHktYXR0cmlidXRlLXNlcnZlcjAeFw0x" +
            "OTEwMTAwOTQzMzRaFw0xOTEwMTcwOTQzMzRaMCcxJTAjBgNVBAMTHHRoaXJkLXBhcnR5LWF0dHJpYnV0ZS1zZXJ2ZXIwggGiMA0GCSqGSIb" +
            "3DQEBAQUAA4IBjwAwggGKAoIBgQDt+hKbJ7Za5WiWteR37r2mgOpTUdRTUR5ikSybu0nL+8RQrK5ur73v573xrzLxMRZ/Duy3hSbzXE/Ku1" +
            "8+IkwXdXDFBTeLbjwOlRskhqNHVE2ly4M49rvDtqtuyGgJIWJci9X1PUmlKQzNBs56+MbBV04NHkphXftKytpaJi6ITih1BrDSaCAvzjpz/" +
            "2DnPFywza/uRuiWv0ubElMN/mZkKye3+u8zdUx+6drqtqHLhGskPtvp1B25bS5bDu2I4uhUqqKhi6XKttSF14IKhPhKv5CnrNS+t5x8/Rxr" +
            "PComZ+5eo2O21TmKk1FenAy2kB+EehyZVXV62zF3ZdGR/GBSBColJSIKZZhRZS+nfKtuLNGkjXElHzXtTsaAKvAx5H+n1aMky8KQNMDa4ez" +
            "bI9K1NVvLSIBUrRMzoS8DLiFA+LpQ2m4FSkt1WINUlGonOE0v8VzVviSS/4QFkMNnQEcUYSvxK65bw0tFlhdZvA0nNFelsms5sBD//sP1Va" +
            "taUaECAwEAAaMyMDAwDgYDVR0PAQH/BAQDAgOYMB4GCysGAQQBgvAXAQEBBA8wDYALVEhJUkRfUEFSVFkwDQYJKoZIhvcNAQELBQADggGBA" +
            "Dwosy4dR2HhJBeKBBfDCDtPfyQRmUQ5yULML/Q4R80oEluLl/zNIwPBJDhQOkLu2s2iOjU3wDXmpm/aeco/YpTNvmFuCOJB/OqyknguEUhH" +
            "hUeWroi5zX1ot5Vrlop5gl412xTcC13EwEXUsicZl857ZgfUKVwdOnbaO/F2VVS+of2wgnh7aQhlzjhaEyWF3qWOczlplbbPffnGtWB5/TJ" +
            "AmAIRh4h2cpaxZ9D5Gog/ayZbKMrah+hKLyh/BZSjqzqykLpXQm5eLc0gfCEELl06eYQcf6hB4ovwadUcSe6PNvso8vJM054y9S7/OzFdAC" +
            "nF7Yeupe5jSD+LOWwZFhlbnNCnfl/BTo6+5BJAtmIOH3leH899eEJiVDT4Ho6ZjFgaF7z4cBBSdu1bqmRAR+Ll/LrYvjdHs2aDCHAL9tTHG" +
            "wkmxcHYWWqHYVlxYvRGDsEbLFLZn8xtBVAuIy3m0K7xesuliEjDRQXfM5iOXK3UZW7xiMtqB1baQjEBJ/FofBqFAwgBEoADGqanaAo4Xvjo" +
            "77y8/CZOBO9ai1ANyuIWpbuJBE49EiRxgJPG4JDqJrRT28U3B8VzRefuJXzsrE4w/gff9FfS97o6XX1vgVa9hb2Uw22dL3xMy7olGCAc+nC" +
            "FtpYYRUq+zCdDm67VglA3qRXCruHUubmDcveFtMrkG2XA3eDaJbu+2E012b2p2FRHJ+UCu/xCYmDgNPQGP3L9wgL1MBd8ROVcw/o8ch065s" +
            "2I++BNanmv9eC1ZGhFGyNMc/p/gkv52GABDwsNEFx2VYfOurgCxTfL7Lqks7ZZU0CPq4Luum8RFdmJRH3xaeSXMWnPjYj1quKUUOaE+X0Ew" +
            "OWJxOGJjFVxUwZTqkBbsGMWuAwR0qK6B56R5CFGB7I8QqmIGNFQj1sk/1x23CvRor2voMdCvbQcs4ulblcl5FrYsNf0idKOGkZ2lXt+SaD1" +
            "G50WSPkEveGpqEvq7QwvnRxAwOszPEF6m4fP9b4rbSy2RYw+Gce6eAGMDXD9dgjwrEyaW3hpIgdvcmdOYW1lKoUDCAESgAMeqKujk0LJqku" +
            "QpIQSAQKqBv91An14cJPE/OZ/U6KuaORSv4+LPRjfzsOZdALVj8jrkKgWJHUFXGGKoGrRH4rbME1CK4v9CgrknHlwjxL41oY3sA8iCC9o0x" +
            "9EXVw2JqSfSFEyJQT0aX8j8CEPxnqs3aagdujaTOOrQZBOqfxIMR1WIE5sYObl9OtPAlW2c7QlDWSf+JdfJ0PjibHgpqVdybLK6/V7lRWU6" +
            "Ai7f6eWnXBHfEVBrU+dCOW0NU0gSG7yAO4avaRoklM0udveQLI4DDZukq6J0e14djWBa7n6O7h+7oXVogUjOfY9V0ewXSyiA9wlinj0n5VJ" +
            "q0fVGw1F1CDsAboQs+axIlHOr6naycQCLXyyYNZfGikHLAU3myzGNh8E/+23dfYxhWP9ZuZdiA/4/py6xkarSsJrhGV67QB4YXTY7iLkKnh" +
            "mUfZnmDMoeVhaFLtyHvELhfABoUxxHuwkxTxy8KH+QztKar8thGDuTAwMoLLYUC3bft+RIoQyRwgBELjbqcOGkuUCGhzBbsvG4CmoKXPBdj" +
            "lkfOCO01SD/tDGmeWHCDSjIhxSZJJ9AFabU/KsPPjHPxe5yaWn3kritey/puBoOgAirg8KN0FOQy1ET0PpBlCOyTGa2/EhdVZgf66KVsfI/" +
            "/gXWPOZXbp0x2dVerDdYFOP2vOJB9wEj11cQVESjggwggQKMIICcqADAgECAhAfD9Wxiox3uC3U5IRaLJn7MA0GCSqGSIb3DQEBCwUAMCcx" +
            "JTAjBgNVBAMTHHRoaXJkLXBhcnR5LWF0dHJpYnV0ZS1zZXJ2ZXIwHhcNMTkxMDEwMDk0MzM0WhcNMTkxMDE3MDk0MzM0WjAnMSUwIwYDVQQ" +
            "DExx0aGlyZC1wYXJ0eS1hdHRyaWJ1dGUtc2VydmVyMIIBojANBgkqhkiG9w0BAQEFAAOCAY8AMIIBigKCAYEA7foSmye2WuVolrXkd+69po" +
            "DqU1HUU1EeYpEsm7tJy/vEUKyubq+97+e98a8y8TEWfw7st4Um81xPyrtfPiJMF3VwxQU3i248DpUbJIajR1RNpcuDOPa7w7arbshoCSFiX" +
            "IvV9T1JpSkMzQbOevjGwVdODR5KYV37SsraWiYuiE4odQaw0mggL846c/9g5zxcsM2v7kbolr9LmxJTDf5mZCsnt/rvM3VMfuna6rahy4Rr" +
            "JD7b6dQduW0uWw7tiOLoVKqioYulyrbUhdeCCoT4Sr+Qp6zUvrecfP0cazwqJmfuXqNjttU5ipNRXpwMtpAfhHocmVV1etsxd2XRkfxgUgQ" +
            "qJSUiCmWYUWUvp3yrbizRpI1xJR817U7GgCrwMeR/p9WjJMvCkDTA2uHs2yPStTVby0iAVK0TM6EvAy4hQPi6UNpuBUpLdViDVJRqJzhNL/" +
            "Fc1b4kkv+EBZDDZ0BHFGEr8SuuW8NLRZYXWbwNJzRXpbJrObAQ//7D9VWrWlGhAgMBAAGjMjAwMA4GA1UdDwEB/wQEAwIDmDAeBgsrBgEEA" +
            "YLwFwEBAgQPMA2AC1RISVJEX1BBUlRZMA0GCSqGSIb3DQEBCwUAA4IBgQCDQQtYSEnbRPonwWvciO+Fk2xtMeBSO7jr4QBZYJGXpCSgcnVk" +
            "dT4x3bZFBFfdekMqiDZjh2asVC38zmFtw/LsvWD8qKcJvkHJxROzavkwa3NPY68B5FqsWNf9GJMNv5usJDEI5cQtTg7CD9rPcL3wIhCEnTW" +
            "UX9o3ffX+0ZohFj1Z54eH2bvf2ZijHQS0Q02xwlznBMAWfUeEWSUcIzjWbnJnvTo1UraZcU8n6laIZW1YmBbEHi3WzpX1uaXl5lo45CsNCV" +
            "A5FpXQr3wvyXdZiFjlZFDGpjdrl84zzR9BuYyhVYPfs+dhnhrqPBGQ6ljDIi7zAhsZVyO+CZ5IPaUdb/xghHofrsUceTREaJeWc4Jap4din" +
            "j+ObgIdS+oBJSfjW0qDV1BeGhzhzMRNdbxH1dX9O8mTcykhxhnVsULyMDOmr/Q8GppZ0iu5pMZ1h00SnTlXOWneSJ3VfXWz7dVyVo3yZVvG" +
            "ai+pdfEVXN8PJzPPucIGwTmoZ1wGOr3nUhUahQMIARKAA1SApS4AMUL0HexT6jmo36KvLu2FD5DHTzPZlhEN/ZDIjvcNFFCBnDRiMG44sx7" +
            "5nV6yR66IsGc1aTEbzkxT8fkgflW7CvL+xLBfK+R+mL4O7WIJ/3BAwUXos7JeFX+ndG+D3f5MndaU0A1yRJAJCDAdeC1CwSSHTrOe63e8QU" +
            "KZXzuTY/offqaiRbtwFASAKIwPX4/Mpshh6by3y4RrBvUHdiht3bYyDagsypVJeEErqYUoMhff17z/tEeqnU3wYM0myxBjGr8LR8Id5jveI" +
            "c9GFrUSintvyCwf57ZyPDDM7BhkbeHkSoNxkpSNgJgcsQmNl+Jw+E7sU+SqAetzZBg3NoprBpCxKGPOnhniSoVCE3j0Po7vZdd8WDWoFyNy" +
            "8ESiiWRx4pjrtKO/Jdz2Tq4RLzGuZ+YKfp7iAyi9N9JQpvG+cCW7Jz2ld4MAZBJLdvgVhzWrISdVyUO8tYQMDgX6ovxZLJScQbYgKganfqm" +
            "yCmzRGTPgfCJNuUWG5yDQVSIHb3JnTmFtZSqFAwgBEoADx16z6tG9tj/kzwtBAKxdsfLqFrrzeoMJLd0ZX/sHesi6Mw7AANBulO3xgS1J7g" +
            "vBCOPCtpHq4wRoINAp6FRbBuLwRhObz8XvCNQ8Bj+2MMNkmOoGSaWYxK5gXI79oUjbhaqxuCTvh/125qMyE9ZgHHsDgt0JKBoFxIwiIaOBB" +
            "t7xbi++id+aBf1SRl0sOrNtGkWibXY4fjLzGMivKeURYhAfIObj+SGHrFoBZ8H6BSwxjptV2EoY7g0aYB5qcFOxfgV9m/Cd5I099SCmAb+H" +
            "eQvazIS4mnK5pQJaRL7iJP/tHI4nLwEdo2NCDQ0SfAsi17ep2rlLbQPuJLVpBhOrCydQSGXj5BanrW1ckUhb5wxV3JYDllkwJPLWYMyk+4y" +
            "F9gtVJt3bC72G6JpgwzeLvqldIa/T18ijd3IUZhS2pdVYRkpOshW9YukiF7VJCaOBnX6vWNaCvnBk0wgDXYmE3Hw9O5B168qT97kfytnIba" +
            "g+5da3KsueSMbOtfRODwR0MkcIARDOs6rDhpLlAhocm39Am37TcJva8vGBHV+ZDTk50Cpp8dT+txlmxCIcjZs7ESHHflixNy92fedcarFJd" +
            "n+OBFieUB1mwzoAMjYKABAAGjD0bvbh0Kk/3cnPNQD2X4fjX/KFQ5gY6kiOjdH/E1yb3ZbYb4dMwk/W9bWpFT+2GTM=";

}
