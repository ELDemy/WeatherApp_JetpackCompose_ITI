package com.dmy.weather.data.enums

enum class AppLanguage(
    val apiCode: String,
    val displayName: String,
    val flagEmoji: String,
    val isRtl: Boolean = false
) {
    SQ("sq", "Shqip", "🇦🇱"),
    AF("af", "Afrikaans", "🇿🇦"),
    AR("ar", "العربية", "🇸🇦", true),
    AZ("az", "Azərbaycan", "🇦🇿"),
    EU("eu", "Euskara", "🏴"),
    BE("be", "Беларуская", "🇧🇾"),
    BG("bg", "Български", "🇧🇬"),
    CA("ca", "Català", "🇪🇸"),
    ZH_CN("zh_cn", "简体中文", "🇨🇳"),
    ZH_TW("zh_tw", "繁體中文", "🇹🇼"),
    HR("hr", "Hrvatski", "🇭🇷"),
    CZ("cz", "Čeština", "🇨🇿"),
    DA("da", "Dansk", "🇩🇰"),
    NL("nl", "Nederlands", "🇳🇱"),
    EN("en", "English", "🇬🇧"),
    FI("fi", "Suomi", "🇫🇮"),
    FR("fr", "Français", "🇫🇷"),
    GL("gl", "Galego", "🇪🇸"),
    DE("de", "Deutsch", "🇩🇪"),
    EL("el", "Ελληνικά", "🇬🇷"),
    HI("hi", "हिन्दी", "🇮🇳"),
    HU("hu", "Magyar", "🇭🇺"),
    IS("is", "Íslenska", "🇮🇸"),
    ID("id", "Bahasa Indonesia", "🇮🇩"),
    IT("it", "Italiano", "🇮🇹"),
    JA("ja", "日本語", "🇯🇵"),
    KR("kr", "한국어", "🇰🇷"),
    KU("ku", "Kurdî", "🏴", true),
    LA("la", "Latviešu", "🇱🇻"),
    LT("lt", "Lietuvių", "🇱🇹"),
    MK("mk", "Македонски", "🇲🇰"),
    NO("no", "Norsk", "🇳🇴"),
    FA("fa", "فارسی", "🇮🇷", true),
    PL("pl", "Polski", "🇵🇱"),
    PT("pt", "Português", "🇵🇹"),
    PT_BR("pt_br", "Português (Brasil)", "🇧🇷"),
    RO("ro", "Română", "🇷🇴"),
    RU("ru", "Русский", "🇷🇺"),
    SR("sr", "Srpski", "🇷🇸"),
    SK("sk", "Slovenčina", "🇸🇰"),
    SL("sl", "Slovenščina", "🇸🇮"),
    ES("es", "Español", "🇪🇸"),
    SV("sv", "Svenska", "🇸🇪"),
    TH("th", "ไทย", "🇹🇭"),
    TR("tr", "Türkçe", "🇹🇷"),
    UK("uk", "Українська", "🇺🇦"),
    VI("vi", "Tiếng Việt", "🇻🇳"),
    ZU("zu", "isiZulu", "🇿🇦");

    companion object {
        val DEFAULT = EN

        fun fromName(name: String?): AppLanguage {
            return entries.find { it.name == name } ?: DEFAULT
        }
    }
}
