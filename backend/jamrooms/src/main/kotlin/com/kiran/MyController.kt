package com.kiran

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Query
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.serde.annotation.Serdeable

@Controller
class MyController(
    private val myClient: MyClient,
    private val myRepo: MyRepo
) {

    @Get("/getPokemon")
    suspend fun testEndpoint(): HttpResponse<*> {
        return myClient.getPokemon()
    }

    @Get("/savePerson")
    suspend fun savePerson(): HttpResponse<*> {
        val name = (1..10)
            .map { ('A'..'Z').random() }
            .joinToString("")
        val person = Person(name)
        myRepo.save(person)
        return ok(person)
    }

    @Get("/getPerson")
    suspend fun getPerson(): HttpResponse<*> {
        val person = myRepo.findByName("Kiran")
        return if (person != null) {
            HttpResponse.ok(mapOf(
                "id" to person.id,
                "name" to person.name
            ))
        } else {
            HttpResponse.noContent<String>()
        }
    }

    @Get("/getAllPeople")
    suspend fun getAllPeople(): HttpResponse<List<Person>> {
        val people = myRepo.findAllPeople()
        return ok(people)
    }
}

@Client("https://pokeapi.co/api/v2")
interface MyClient {
    @Get("/pokemon/ditto")
    suspend fun getPokemon(): HttpResponse<*>
}

@Serdeable
@MappedEntity
data class Person(
    var name: String
) {
    @GeneratedValue
    @Id
    var id: Long? = null
}

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface MyRepo: CoroutineCrudRepository<Person, Long> {
    suspend fun findByName(name: String): Person?

    @Query("SELECT * FROM person")
    suspend fun findAllPeople(): List<Person>
}