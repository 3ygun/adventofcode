package adventofcode.y2018

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day5Tests : StringSpec({
    "Day 5, Star 1" {
        forAll(
            row("dabCBAcaDA", "dabAcCaCBAcCcaDA"),
            row("aabAAB", "aabAAB"),
            row("", "DaAd"),
            row("DD", "DaACcD"),
            row("", "aA"),
            row("", "Aa"),
            row("xx", "xxAa"),
            row("YY", "YYAa"),
            row("v", "vvVVv"),
            row("dabCBAcaDAdabCBAcaDAdabCBAcaDAdabCBAcaDAdabCBAcaDA", "dabAcCaCBAcCcaDAdabAcCaCBAcCcaDAdabAcCaCBAcCcaDAdabAcCaCBAcCcaDAdabAcCaCBAcCcaDA"),
            row(STAR1_RESULT, STAR1_RESULT),
            row(STAR1_RESULT, Day5.STAR1_DATA) // Purposed solution
        ) { result, input ->
            Day5.star1Calc(input) shouldBe result
        }
    }
    "Day 5, Star 2" {
        forAll(
            row(4, "dabAcCaCBAcCcaDA"),
            row(0, "aabAAB"),
            row(0, "DaAd"),
            row(0, "DaACcD"),
            row(0, "aA"),
            row(0, "Aa"),
            row(0, "xxAa"),
            row(0, "YYAa"),
            row(0, "vvVVv"),
            row(3, "xyza"),
            row(20, "dabAcCaCBAcCcaDAdabAcCaCBAcCcaDAdabAcCaCBAcCcaDAdabAcCaCBAcCcaDAdabAcCaCBAcCcaDA"),
            row(4052, Day5.STAR2_DATA)
        ) { result, input ->
            Day5.star2Calc(input) shouldBe result
        }
    }
})

private const val STAR1_RESULT = "XuBmtLrFCwtRmTbWtxnxHgdqFkePlzyVOJFcPTUwcmhoBdcbPIKCPHWAYrUargFrxqvDxNBdxVIvoRWlWUQMNzjnFMWmpHtwxoocoGGZxbAFCEAHWeddnTKIGKprVrzXnuVbRAUqDYLkUtQRCMlZAHClrzuOWMzvAdXcrbWzHMLPeRVkjQyPKHECrIDTLWSAJAJzwvryXWQIITrHFNtQaBSVjdLHHWEmRgkpUifIBpYRDJqWQvNRloQEcGCCErqqjhtVklVMObUdaUxQrdAOTiWnWHqWEffuJuKKRobmaWpNsPjYhoCQtMHBXgOXgNGoEoFlPAeDfYcPxUacbRxyrbOZyytrMwkWsXEwfIjcnCCpWQYWTZwmsCmlKxhsdzVVcirmtImEQKTnAidboFzFwpeJJamdeAXpMUUBJBXRWKInyOLtuTleeAQCQJygJFpipbyXqooTXHRJgVKrDqSRkQVGXweZrbvghfWquQmGSDFFwjUGjQDSFGDTNjTNhGllkxCbLHqwynqhCtwajziQBnlhWiKziaCbvTzPYavgnIBOFCZlmFhaowqkghCzasGLRFrzCgaIKiCyUlvPEIsOlPiWKNQvkQYEJvHxyMAgaQwLvHcWQZbDfqrkuiAvqaUpqiFvMoLCfdpjbmFnIVqvDQHxEMCPfNFZwBPmvvoWtjcYWlldLuPjfewxSaXpDOWJWfhtWSeZiWNBajaxWYbiJLPnOOAcWrNrHqwXjLKZqEKDHBzaJWXJELvCiUQTSnnVcszumoIXFSpTlRapYfWZCZqWLFxNuCuQUiuLHNULDqdkyBGXVbhsRmKJwrjrmUZZxOxLhZdqbdtsBHbpCpjOqrPPPncNGMtMohzznBdUcgTAHLofnOcOmJmvgoFBgldFdCOMVZONDBqVjSvXODmEHEGhQtaJLtlFBhanztBRAthTUgYduEdNcwBtPUBqahfIfTqqArknwmDsAwcBGQnCusBrgjOFZsYpfkfTQoLPTkJOGXACDrZmpNEEJtGYgAmDpkGOkWjoRyArzNYHiZQHOysrpcnTPYHCBxCvbwEXeIlFuzQsDYkEVQdJICVKyqMbrVXMwOOyJcSgsuQTzLPEXZmpeWaVSkPxJnfPZlQlPhYcHVpCrfelBzmAXnCpacJsDgWUhtubXMYdrCGoRSIWeFRGfhGhXnsfZVeKOeNsIpYZWtKaqvjuiAZNKaNoEgxLocwAeFCBhAROJQGPfutcoNMkpvxigiGWFGiBrwsQyqCugjyLRDfidsDGNvvriJQttrHGwwHaXpEXFQWSihTWqfPMaxVkNKCpQaKQSyqRFzgQeiGjquwyBunEVmjKTlPFEmxfcneQLkLLOrOFtCgmVjsvTqGmzPVLbKwFtNKXgCCszWvXoDNKMKigABdSEnrNVqislwuvcSSzHrgoEMMuKnidpWkxkfkRZpdEEXHscqnUEsHgIkERPwYChcpJYTQvMaOPGeuQbDYIEQYthuRiHxIbqVqPqkycJVxnLmcSCRjTwqWjwjuvGPXQomzKqDeVtEVUOzyVECaXorGJYOYIIKrBhmPVRcIPbDLYLxUqSLrQOOOLxzeuhwIEtuLhrjdWorIRPcdHLYTQWWdZQreIlWodAyoZLnxChUSqlqXmVxFmffnOnaoSXYXzlrokPCoblCZFPWRlKZyIdXvogzwxEiuzLHkycfgHffLftlIPjXVxpOEDybyekLtpSVMigyADuaQlvnQuVZOMjUsVZZqGleNSkRGbqoyNShlBSdhsvWYeUOyRsPUhIQNONfGYziVFFcyWhQwRuTAirimmVGKJbVCsabvQORnQfYNtDerSXWuFNuWWBSOfRDQSHEBPIbwextvyjCbCgqSvaohbxxeLYpUeHtQnqJYjnyUhcDioyrlmyMvxplFEByWsdwLSBijOblTvYUyWBTjZjCRUMTisrPzTAHgNvUZYEbmqdZgEZcaeGGagzsgymlefpyPDOuuElmnlkqyKpKPdvjKjxQZycNCIULMYacuWeoCajvIKKrzBrhGFjUOcfKiSxqaaMTXhOefLiBTPaKCMfxEMkngDoGcQvhqJnIIciXtddgDhcJTzeYDtoZmJYTnCGIhMrMhICwOlOLknSYEJSCmJnHVAlFIUjtJmIpujklSbuXaJGxJmqgUdJPEMZRHjyGwIBEmPXFrpjCVwdwSUiLDcZQJGKeagzvMXdzGJATQPvBVYNeZBdUvBpWYMDKjErrGMqtOgjZABJYvwKpKqPIyvBVFKEZnLppinbyOQwjHDGoIQbDKLHAwGkaBfzmNHZVjBJsyZPXMaxLIxhvKfOZqsZMDXYuQZVuBHciJlklxENiHZkMethmxrNpoByZnDKOChTMFvbdGwXBZpXjWKDwijfSvufSPiOFcyztFyzKcpoMYxPNlMnnKHqZLQpePjNMMvdCJHvoYELsdLVCiGDjKjvjDInpYsPRlSxseKDZQPmveSxhghtcvpNWQrfbSGfqIoTCnxKJkAsAXjydSEwGEHnAOZnRqXkiQTINmcMcNvXEPinrHaoKoRQKBsRdCkzsQrATZbPbQJUekNptDketxbcbVIkUzDKIxTbWkcEnknrYbMFeFbtlEMcvyOWHuhvsDIAdtwFZWVrkxUFmrxQcRXbiNqHSPZXMadQNrjZvfLNOqEDncqinjAjrrKjawkZEhGQwarLXkagtkywvWWZmUBrCddjSogDIrnoVovJKlNRzQJgumKXsNdKryDhYSXQriMphgbqEyfPmDZftWkHAZXpzBUaXohTvvKHXWixwzBokdHQiefqpHqNUrAvELNPWFizTQCHcALuwxUrrXmJdXJYAywPYsLRzbvwkjwnfXnBRWvABnpjnkFoGmeFDSOdovgFqhUXObplKTZPEWbDPvvIMbLhsHsavAvEpGMgUOaBAsURmRSEVbilhdYaDrkakySejXPIrDmqOLjgnikJgXBVUbmurshoGQJdnsgfcUqhNCoGusUGbuVDyKarPzkzAqEmfikasWNmGmusRzvSxcWnsUrazeSclMYnZELpmtslIDpxjgpAPYwQQrovOaGjmaousmucxvvukVjjRhqLYCyECBNcygYmEPlWKKwpkqunPAkhpCXqiuEEgZDGStmrkOKAkvYuYuFkqxOkXfxUnkeQULQwEzUYMSUKfurBRhiikbiyTbnMLCCkCjyPUewdnrakaCUMkVsDxalKhZphomKcKSmvmCQKLRgWQnbfUkaNmWSMXVcEoeKFUVJMuiaECsFlmSNtbYmwUbSAEsDYsfUqxBrChGVkhMGErqHLhJlXDkemHkMgYnJIBiPWKQrpAyuQGUsInQWQraKAfiVzdsJQrZARSGiEpkOiQnpQvPTlsDWBEpeQvYoVGSbsyFzoeZXTeIEPYRmmshcffgiPFobxaJMococGduCBmwVPJToDIGQFbaHAfXpAnknGBNJiPVOMlbVhQWLZFQTCGiGPgorowmkNkAzzoQFspxbUxoEhGTQMFhVFOsgqXajRPhwkcYiBhJxIcuyGpVcSagxqFoMVevKcPSLLGByBuMDpXTWBobTVbgXZtzPGDgbCBmvYzgUaFaQvOvlOWsezFjoXCYoRMxAzSEpIzUosPdspVJIdaqWQtCeGPfEulwSjtLoRDwMtOXWXlAyeVFjddPLUBOvmmWPwbEusHUlRRygnoYtUEBKPxCqBUCgUAQGbjgFvAJqMMktmrYxgAgjBCKdPSnmDWNbywRRiBURNKzxogvGzKmldHCZDyiPSHGlyxvEZgxwaNYhejSNYLHKXGwVwiymNEPeFiHipvrLJBEYxpvKXvMsWVHPCuLFIqtGEhDVqVfYokRfPJojPVKbdRywLRNEHanzygbXjwqHYlfqaQLvZkhbqbKLmqsCvbboVXlUMfruBdcFimYPfngcPkrkzJqCMCTgAROBsNkAXqBuekaLFzMOAFRorCjIfiLMiWlrdvjKcjFlllPHsyxgaTSqwbRPuolINHDQcdpkyWDVTfirNvYAeipVUzIZsVpiHYKiSLQBTzWMCKplhDnkPcQTqPVBeCFQMiPVamsgUcteREqelkoiYtfNgmmauGeAfbcJATJPXBacyjoTrMQoMuZdSduBhzgRyKrrrFUhegNLhueHcEcbedbdRSaDHzmQwfYtDjaxbptgWuseUgvILwMUoqnOVgFUAmsFPDzPVauwoFLzEVGmhJeFjsQIxkOwFhLLqjaXbwYVatpJtAGIpvwvLsDcvjmLeSyXYdXpscjEtSKCjsyIZVGUHEvRqhUOchZDzyraOAVTcAttbRxoQLipcpNvpxLAAWULNcUcQgflKgtOVSiMaNjiuWeyEXcKQnKndZrCFoAzGwmzcAFSvJJPORxnJdthGaczQXadCRRPlpIQPjWtDFMaxaDZUeqJOkGGYdufTfGcvvHabTkXOXGwfysGvHvYLgUZYMvajYrLVmiSUKcHOQnfdhqUNzRijygPQTbRWipIVDpTwAYTmwhDUbPHtjKpDpvwVZUnUpkhBhTgAirgpdBhYdYDmslrZXiBdXVeuZgyvucbEoloVAtgJftcyyhjMWOtVKrPQvjBLVJODpBHZVVuQvuukuthPPXokItOZESebfzGhpoEbRkZKrBeOPHgZFBEsezoTiKOxppHTUKUUVqUvvzhbPdojvlbJVqpRkvTowmJHYYCTFjGTavOLOeBCUVYGzUEvxDbIxzRLSMdyDyHbDPGRIaGtHbHKPuNuzvWVPdPkJThpBudHWMtyaWtPdviPIwrBtqpGYJIrZnuQHDFNqohCkusIMvlRyJAVmyzuGlyVhVgSYFWgxoxKtBAhVVCgFtFUDyggKojQEuzdAXAmfdTwJpqiPLprrcDAxqZCAgHTDjNXropjjVsfaCZMWgZaOfcRzDNkNqkCxeYEwUIJnAmIsvoTGkLFGqCuCnluwaalXPVnPCPIlqOXrBTTaCtvaoARYZdzHCouHQrVehugvziYSJcksTeJCSPxDyxYsElMJVCdSlVWVPigaTjPTAvyWBxAJQllHfWoKXiqSJfEjHMgveZlfOWUAvpZdpfSMaufGvoNQOumWliVGuESUwGTPBXAJdTyFWqMZhdAsrDBDEBCeChEUHlnGEHufRRRkYrGZHbUDsDzUmOqmRtOJYCAbxpjtajCBFaEgUAMMGnFTyIOKLEQerETCuGSMAvpImqfcEbvpQtqCpKNdHLPkcmwZtbqlsIkyhIPvSziZuvPIEayVnRIFtvdwYKPDCqdhniLOUprBWQstAGXYShpLLLfJCkJVDRLwImlIFiJcROrfaomZflAKEUbQxaKnSboraGtcmcQjZKRKpCGNFpyMIfCDbURFmuLxvOBBVcSQMlkBQBHKzVlqAQFLyhQWJxBGYZNAhenrlWYrDBkvpJOjpFrKOyFvQvdHegTQiflUcphvwSmVxkVPXyebjlRVPIhIfEpenMYIWvWgxkhlynsJEHynAWXGzeVXYLghspIYdzchDLMkZgVGOXZknrubIrrWYBnwdMNspDkcbJGaGXyRMTKmmQjaVfGJBgqauGcubQcXpkbeuTyONGYrrLuhSUeBWpwMMVobulpDDJfvEYaLxwxoTmWdrOlTJsWLUeFpgEcTqwQADijvPSDpSOuZiPesZaXmrOycxOJfZESwoLVoVqAfAuGZyVMbcBGdgpZTzxGBvtBObwtxPdmUbYbgllspCkVEvmOfQXGAsCvPgYUCiXjHbIyCKWHprJAxQGSofvHfmqtgHeOXuBXPSfqOZZaKnKMWOROGpgIgctqfzlwqHvBLmovpIjnbgNKNaPxFahABfqgidOtjpvWMbcUDgCOCOmjAXBOfpIGFFCHSMMrypeiEtxzEOZfYSBsgvOyVqEPebwdSLtpVqPNqIoKPeIgsrazRqjSDZvIFakARqwqNiSugqUYaPRqkwpIbijNyGmKhMEKdxLjHlhQRegmHKvgHcRbXQuFSydSeasBuWMyBTnsMLfSceAIUmjvufkEOeCvxmswMnAKuFBNqwGrlkqcMVMskCkMOHPzHkLAXdSvKmucAKARNDWEupYJcKcclmNBtYIBKIIHrbRUFkusmyuZeWqluqEKNuXFxKoXQKfUyUyVKakoKRMTsgdzGeeUIQxcPHKapNUQKPWkkwLpeMyGYCnbceYcylQHrJJvKUVVXCUMSUOAMJgAoVORqqWypaPGJXPdiLSTMPlezNymLCsEZARuSNwCXsVZrSUMgMnwSAKIFMeQaZKZpRAkYdvUBguSUgOcnHQuCFGSNDjqgOHSRUMBuvbxGjKINGJloQMdRipxJEsYKAKRdAyDHLIBvesrMruSabAouGmgPeVaVAShSHlBmiVVpdBwepztkLPBoxuHQfGVODosdfEMgOfKNJPNbaVwrbNxFNWJKWVBZrlSypWYayjxDjMxRRuXWUlaChcqtZIfwpnleVaRunQhPQFEIqhDKObZWXIwxhkVVtHOxAubZPxzahKwTFzdMpFYeQBGHPmIRqxsyHdYRkDnSxkMUGjqZrnLkjVOvONRidGOsJDDcRbuMzwwVWYKTGAKxlRAWqgHezKWAJkRRJaJNIQCNdeQonlFVzJRnqDAmxzpshQnIBxrCqXRMfuXKRvwzfWTDaidSVHUhwoYVCmeLTBfEfmByRNKNeCKwBtXikdZuKivBCBXTEKdTPnKEujqBpBztaRqSZKcDrSbkqrOkOAhRNIpexVnCmCMnitqIKxQrNzoaNhegWesDYJxaSaKjkXNctOiQFgsBFRqwnPVCTHGHXsEVMpqzdkESXsLrpSyPNidJVJkJdgIcvlDSleyOVhjcDVmmnJpEPqlzQhkNNmLnpXymOPCkZYfTZYCfoIpsFUVsFJIWdkwJxPzbxWgDBVfmtHcokdNzYbOPnRXMHTEmKzhIneXLKLjIChbUvzqUyxdmzSQzoFkVHXilXAmxpzYSjbJvzhnMZFbAKgWahlkdBqiOgdhJWqoYBNIPPlNzekfvbVYipQkPkWVyjbazJGoTQmgRReJkdmywPbVuDbzEnyvbVpqtajgZDxmVZGAEkgjqzCdlIusWDWvcJPRfxpMebiWgYJhrzmepjDuGQMjXgjAxUBsLKJUPiMjTJuifLavhNjMcsjeysNKloLoWciHmRmHigcNktyjMzOTdyEZtjCHdGDDTxIKCiiNjQHVqCgOdGNKmeXFmckAptbIlFEoHxtmAAQXsIkFCouJfgHRbZRkkiVJAcOEwUCAymluicnCYzqXJkJVDpkPkYQKLNMLeUUodpYPFELMYGSZGAggEACzeGzDQMBeyzuVnGhatZpRSItmurcJzJtbwYuyVtLBoJIbslWDSwYbefLPXVmYMLRYOIdCHuYNJyjQNqThEuPylEXXBHOAVsQGcBcJYVTXEWBipbehsqdrFosbwwUnfUwxsREdTnyFqNroqVBAScvBjkgvMMIRIatUrWqHwYCffvIZygFnonqiHupSrYouEywVSHDsbLHsnYOQBgrKsnELgQzzvSuJmozvUqNVLqAUdaYGImvsPTlKEYBYdeoPXvxJpiLTFlFFhGFCYKhlUIeXWZGOVxDiYzkLrwpfzcLBOcpKORLZxyxsOANoNFFMfXvMxQLQsuHcXNlzOYaDOwLiERqzDwwqtylhDCpriROwDJRHlUTeiWHUEZXloooqRlsQuXlyldBpiCrvpMHbRkiiyoyjgROxAcevYZouveTvEdQkZMOqxpgVUJWJwQWtJrcsCMlNXvjCYKQpQvQBiXhIrUHTyqeiydBqUEgpoAmVqtyjPCHcyWpreKiGhSeuNQCShxeeDPzrKFKXKwPDINkUmmeOGRhZssCVUWLSIQvnRNesDbaGIkmkndOxVwZSccGxknTfWkBlvpZMgQtVSJvMGcTfoRollKlqENCFXMefpLtkJMveNUbYWUQJgIEqGZfrQYsqkAqPcknKvXAmpFQwtHIswqfxePxAhWWghRTTqjIRVVngdSDIFdlYJGUcQYqSWRbIgfwgIGIXVPKmnOCTUFpgqjoraHbcfEaWCOlXGeOnAknzaIUJVQAkTwzyPiSnEokEvzFSNxHgHFgrfEwisrOgcRDymxBUTHuwGdSjCAPcNxaMZbLEFRcPvhCyHpLqLzpFNjXpKsvAwEPMzxeplZtqUSGsCjYooWmxvRBmQYkvcijDqveKydSqZUfLiExeWBVcXbchyptNCPRSYohqzIhynZRaYrOJwKogKPdMaGygTjeenPMzRdcaxgojKtplOqtFKFPySzfoJGRbSUcNqgbCWaSdMWNKRaQQtFiFHAQbupTbWCnDeUDyGutHTarbTZNAHbfLTljATqHgeheMdoxVsJvQbdnozvmocDfDLGbfOGVMjMoCoNFOlhatGCuDbNZZHOmTmgnCNpppRQoJPcPBhbSTDBQDzHlXoXzzuMRJRWjkMrSHBvxgbYKDQdlunhlUIuqUcUnXflwQzczwFyPArLtPsfxiOMUZSCvNNstquIcVlejxwjAZbhdkeQzklJxWQhRnRwCaooNpljIBywXAJAbnwIzEswTHFwjwodPxAsXWEFJpUlDLLwyCJTwOVVMpbWzfnFpcmeXhqdVQviNfMBJPDFclOmVfIQPuAQVaIUKRQFdBzqwChVlWqAGamYXhVjeyqKVqnkwIpLoSiepVLuYcIkiAGcZRfrlgSAZcHGKQWOAHfMLzcfobiNGVAypZtVBcAIZkIwHLNbqIZJAWTcHQNYWQhlBcXKLLgHntJntdgfsdqJguJWffdsgMqUQwFHGVBRzEWxgvqKrsQdRkvGjrhxtOOQxYBPIPfjGYjqcqaEELtUTloYNikwrxbjbuumPxaEDMAjjEPWfZfOBDIaNtkqeMiTMRICvvZDSHXkLMcSMWztwyqwPccNCJiFWexSwKWmRTYYzoBRYXrBCAuXpCyFdEapLfOeOgnGxoGxbhmTqcOHyJpSnPwAMBOrkkUjUFFewQhwNwItoaDRqXuADuBomvLKvTHJQQReccgCeqOLrnVqwQjdryPbiFIuPKGrMewhhlDJvsbAqTnfhRtiiqwxYRVWZjajaswltdiRcehkpYqJKvrEplmhZwBRCxDaVZmwoUZRLchazLmcrqTuKlydQuarBvUNxZRvRPkgiktNDDEwhaecfaBXzggOCOOXWThPMwmfNJZnmquwLwrOVivXDbnXdVQXRfGAuRyawhpckipBCDbOHMCWutpCfjovYZLpEKfQDGhXNXTwBtMrTWcfRlTMbUx"
