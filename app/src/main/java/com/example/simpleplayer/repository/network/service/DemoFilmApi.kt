package com.example.simpleplayer.repository.network.service

import io.reactivex.Single
import java.util.concurrent.TimeUnit

class DemoFilmApi {

    // I could not find a normal service for getting links to files

    private val filmNameList = mapOf(
        "AVENGERS: ENDGAME"
                to "https://www.kinomania.ru/load/n?file=//fs.kinomania.ru/media/video/a/0f/a0f56bb43799522951d7a3d3a5b4e597.720.mp4",
        "THE LION KING"
                to "https://www.kinomania.ru/load/n?file=//fs.kinomania.ru/media/video/9/14/914a7f31b27c8c6409439c184b8adedd.720.mp4",
        "JOKER" to "https://www.kinomania.ru/load/n?file=//fs.kinomania.ru/media/video/f/28/f28543cd7a80815cc0ce17893f2ac0ad.720.mp4",
        "THE SECRET LIFE OF PETS 2"
                to "https://www.kinomania.ru/load/n?file=//fs.kinomania.ru/media/video/d/97/d97eb74707d1f97526a360674e5933a8.720.mp4",
        "SPIDER-MAN: FAR FROM HOME"
                to "https://www.kinomania.ru/load/n?file=//fs.kinomania.ru/media/video/2/af/2af541655e5a9788ffd5da73c5165916.720.mp4",
        "CAPTAIN MARVEL"
                to "https://www.kinomania.ru/load/n?file=//fs.kinomania.ru/media/video/e/2b/e2b2ad10a0cce81f7b1757538471f089.720.mp4",
        "HOW TO TRAIN YOUR DRAGON 3"
                to "https://www.kinomania.ru/load/n?file=//fs.kinomania.ru/media/video/7/3b/73bafebc1719de480f0970b3a6fa405d.720.mp4",
        "ONCE UPON A TIME IN HOLLYWOOD"
                to "https://www.kinomania.ru/load/n?file=//fs.kinomania.ru/media/video/b/66/b661d2e56f9c4a779f45f39c7f498ec2.720.mp4",
        "FAST & FURIOUS PRESENTS: HOBBS & SHAW"
                to "https://www.kinomania.ru/load/n?file=//fs.kinomania.ru/media/video/4/ec/4ecbcd40187fe5abe6ab91aeb1a53205.720.mp4",
        ("SHAZAM!"
                to "https://www.kinomania.ru/load/n?file=//fs.kinomania.ru/media/video/6/da/6dafab5f86d9e5f32ba9ce303d86098f.720.mp4")
        )

    fun getFilmNameList(): Single<Map<String, String>> {
        return Single.just(filmNameList).delay(500, TimeUnit.MILLISECONDS)
    }
}