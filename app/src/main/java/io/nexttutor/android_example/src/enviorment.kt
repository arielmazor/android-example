package io.nexttutor.android_example.src


val enviorment = SandboxEnviorment()

data class SandboxEnviorment(
    val SQ_APPLICATION_ID: String = "sandbox-sq0idb-Cxs5eSor630oJSrejFzR4A",
    val SQ_APPLICATION_SECRET: String = "sandbox-sq0csb-HGIZzadrZo98i90qSScomGICbQcqw7G0U7HtJ9uRegQ",
    val SQ_NEXT_TUTOR_ACCESS_TOKEN: String = "EAAAEDYPn-p_yT7R0KuJiugkHptuCAaVHhrNoDNBhP3LW2vyz0BUf57ejl8DCfSc",
    val SQ_TUTOR_ACCESS_TOKEN: String = "EAAAEDYPn-p_yT7R0KuJiugkHptuCAaVHhrNoDNBhP3LW2vyz0BUf57ejl8DCfSc",
    val SQ_API_URL: String = "https://squareupsandbox.com",
    val API_URL: String = "http://localhost:1111"
)

data class ProductionEnviorment(
    val SQ_APPLICATION_ID: String = "sandbox-sq0idb-Cxs5eSor630oJSrejFzR4A",
    val SQ_APPLICATION_SECRET: String = "sandbox-sq0csb-HGIZzadrZo98i90qSScomGICbQcqw7G0U7HtJ9uRegQ",
    val SQ_API_URL: String = "https://squareup.com",
    val API_URL: String = "http://localhost:1111"
)