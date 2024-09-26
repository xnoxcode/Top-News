package epm.xnox.topnews.domain.model

data class Article(
    val id: Int,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) {
    constructor() : this(
        id = 0,
        author = "",
        content = "",
        description = "",
        publishedAt = "",
        source = Source(id = "", name = ""),
        title = "",
        url = "",
        urlToImage = ""
    )
}
