
import com.github.movie.domain.models.RatingValue
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailInfo(
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: String,
    @Json(name = "Rated")
    val rated: String,
    @Json(name = "Runtime")
    val runtime: String,
    @Json(name = "Genre")
    val genre: String,
    @Json(name = "Plot")
    val plot: String,
    @Json(name = "Poster")
    val poster: String,
    @Json(name = "Ratings")
    val rating: List<RatingValue>
)
