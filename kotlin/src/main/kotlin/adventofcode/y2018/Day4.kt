package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object Day4 : Day {
    private val LOG_REGEX = Regex("\\[(.+)\\] (.+)")
    private val SHIFT_BEGINS_REGEX = Regex("Guard #(\\d+) begins shift")
    private val LOG_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val STAR1_DATA = DataLoader.readLinesFromFor("/y2018/Day4Star1.txt")
    val STAR2_DATA = STAR1_DATA

    override val day: Int = 4

    override fun star1Run(): String {
        val result = star1Calc(STAR1_DATA)
        return "Guard ID ${result.guardId} slept the most and usually at ${result.favoriteMinute} results in: ${result.guardIdTimesFavoriteMinute()}"
    }

    fun star1Calc(rawInputs: List<String>): GuardIdWithFavoriteMinute {
        return parseLogs(rawInputs)
            .let { parseSortedTimeTextEntries(it) }
            .maxBy { it.timeSlept }!!
            .let { GuardIdWithFavoriteMinute(
                guardId = it.guardId,
                favoriteMinute = it.favoriteMinute()
            ) }
    }

    override fun star2Run(): String {
        val result = star2Calc(STAR2_DATA)
        return "Guard ID ${result.guardId} was mostly slept at ${result.favoriteMinute} results in: ${result.guardIdTimesFavoriteMinute()}"
    }

    fun star2Calc(rawInputs: List<String>): GuardIdWithFavoriteMinute {
        return parseLogs(rawInputs)
            .let { parseSortedTimeTextEntries(it) }
            .map { it.favoriteMinuteBySleepCount() }
            .maxBy { it.sleptCount }!!
            .let { GuardIdWithFavoriteMinute(
                guardId = it.guardId,
                favoriteMinute = it.minute
            ) }
    }

    private fun parseLogs(rawInputs: List<String>): List<TimeAndText> {
        return rawInputs.map { input ->
            val groups: List<String> = LOG_REGEX.matchEntire(input)?.groupValues
                ?: throw IllegalArgumentException("Could not match log: $input")
            val time = LocalDateTime.parse(groups[1], LOG_DATE_TIME_FORMAT)
            val text = groups[2]

            TimeAndText(time, text)
        }.sorted()
    }

    private fun parseSortedTimeTextEntries(input: List<TimeAndText>): List<GuardWatch> {
        val guardIdToWatch = HashMap<Int, GuardWatch>()
        var lastGuard: GuardWatch? = null

        for ((time, text) in input) {
            when (text) {
                "falls asleep" -> lastGuard!!.sleepsAt(time)
                "wakes up" -> lastGuard!!.wakesUpAt(time)
                else -> {
                    val ids: List<String> = SHIFT_BEGINS_REGEX.matchEntire(text)?.groupValues
                        ?: throw IllegalArgumentException("Could not match guard shift log: $text")
                    val id = ids[1].toInt()
                    lastGuard =  guardIdToWatch.getOrPut(id) { GuardWatch(id) }
                }
            }
        }

        return guardIdToWatch.values.toList()
    }


    data class GuardIdWithFavoriteMinute(
        val guardId: Int,
        val favoriteMinute: Int
    ) {
        fun guardIdTimesFavoriteMinute() = guardId * favoriteMinute
    }

    data class TimeAndText(
        val time: LocalDateTime,
        val text: String
    ): Comparable<TimeAndText> {
        override fun compareTo(other: TimeAndText): Int {
            return time.compareTo(other.time)
        }
    }

    data class GuardWatch(val guardId: Int) {
        var timeSlept = 0
        private var startedSleeping: LocalDateTime? = null
        private val minuteStartAndElapsed: MutableList<MinuteStartAndElapsed> = ArrayList()

        fun sleepsAt(time: LocalDateTime) {
            assert(startedSleeping == null) { "Guard Id $guardId, was already asleep! Starting at $startedSleeping. Now sleeping again at $time" }
            startedSleeping = time
        }

        fun wakesUpAt(time: LocalDateTime) {
            assert(startedSleeping != null) { "Guard Id $guardId, was not asleep! Thus they cannot wake up at $time" }

            val startedSleepingAtSecond = startedSleeping!!.toEpochSecond(ZoneOffset.UTC)
            val endedSleepingAtSecond = time.toEpochSecond(ZoneOffset.UTC)
            val elapsedMinutes = ((endedSleepingAtSecond - startedSleepingAtSecond) / 60).toInt() // seconds to minutes

            minuteStartAndElapsed.plusAssign(MinuteStartAndElapsed(
                minuteStart = startedSleeping!!.minute,
                minutesElapsed = elapsedMinutes
            ))
            timeSlept += elapsedMinutes
            startedSleeping = null
        }

        fun favoriteMinute(): Int {
            return favoriteMinuteBySleepCount().minute
        }

        fun favoriteMinuteBySleepCount(): GuardIdMinuteAndSleptCount {
            var maxLast = 0
            var favoriteMinute = -1
            sleptMinutesDistribution().forEachIndexed { index, i ->
                if (i > maxLast) {
                    maxLast = i
                    favoriteMinute = index
                }
            }
            return GuardIdMinuteAndSleptCount(
                guardId = guardId,
                minute = favoriteMinute,
                sleptCount = maxLast
            )
        }

        private fun sleptMinutesDistribution(): IntArray {
            val minutes = IntArray(60)

            minuteStartAndElapsed.forEach {
                for (i in it.minuteStart until (it.minuteStart + it.minutesElapsed)) {
                    val index = i % 60
                    minutes[index] = minutes[index] + 1
                }
            }

            return minutes
        }
    }

    data class MinuteStartAndElapsed(
        val minuteStart: Int,
        val minutesElapsed: Int
    )

    data class GuardIdMinuteAndSleptCount(
        val guardId: Int,
        val minute: Int,
        val sleptCount: Int
    )
}
