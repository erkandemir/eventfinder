import com.example.eventfinder.api.Api

open class BaseRepository<T>(service: Class<T>) {
    var client : T
    init {
        client = Api.buildService(service)
    }
}