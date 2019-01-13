package adventofcode.y2018.data

object Day1Data {
    fun star1() = star1Data.lines()

    fun star2() = star2Data.lines()
}

private const val star1Data = """
+13
+9
+8
+1
-15
+10
+16
+17
+8
+8
+17
+6
+17
+18
-10
-16
+13
+15
-18
-11
-2
+7
-14
-4
+17
+18
-6
+3
-4
-2
-18
-23
+7
-5
-14
-12
+16
+16
+10
+16
-4
-21
-11
-16
-17
+3
-1
+19
+13
+18
+4
+19
-2
+13
+20
+4
-16
+6
+11
-13
-1
+19
+9
+5
+13
+14
-19
+8
-13
-5
+11
-15
-9
+5
-3
+1
-15
-1
+19
+21
+2
+1
+10
-1
+5
-6
+13
+7
-1
+7
+8
-19
+8
-13
+4
+16
+11
+7
+6
-14
+19
+3
+19
+18
+13
+19
+8
+15
+6
-9
+8
+17
-12
+2
+6
+14
-9
+8
+13
+6
+5
+19
-16
+14
-9
+7
+19
-18
-3
-15
-19
-10
+2
-17
+19
-8
-19
+7
-16
-8
+2
-18
-1
+9
+19
-17
+3
+1
+6
-14
-2
+7
-4
+14
-6
+8
+2
+5
+21
+18
+3
+8
-13
+17
-19
+13
+5
-15
+13
-8
+6
+14
+16
+13
+12
+17
+12
+17
-11
+1
+16
-19
-17
-14
-8
+3
+12
+13
+9
-1
-12
+5
-12
+15
+1
+1
+9
+8
+10
-6
+4
-2
+7
+4
-1
-2
+10
+12
+4
-6
+1
+17
+5
-10
-19
-9
-17
+18
-15
+6
+18
+19
-17
+15
-14
-2
+15
-2
-10
-18
-21
+3
-2
-16
+1
-4
+17
-18
-14
+2
+11
-18
+11
-8
-14
-10
+2
-5
-15
-8
-18
-8
+13
+19
+14
-9
+11
-5
+10
+3
+21
-20
-7
+5
+6
-3
+13
-4
+1
-3
-20
-9
-7
+10
+12
+13
+11
+12
-5
+4
+29
-9
-21
-15
+34
+38
+39
+14
+20
+3
-11
-15
+12
+22
-23
-19
-14
+6
+11
+15
-17
+12
+16
+3
+21
-10
+17
+21
+17
-3
-19
-11
-6
+20
+5
+17
+12
+8
+12
+13
+4
-1
+23
-8
+11
+17
+7
-9
-2
-11
+8
+9
+4
+19
+13
-14
+19
+17
-18
-6
+12
-19
-6
+7
+7
+9
-6
-18
+5
-6
-17
+14
-3
-16
-13
-11
+16
+19
-2
+5
+3
-7
-15
-10
+13
+8
-16
-12
-9
-19
-18
-11
-5
+13
-17
+3
+16
+25
-15
+22
-18
-17
+5
+4
-20
-9
+5
-18
+5
-23
+3
-16
+12
-4
-7
+21
-4
+7
+14
+15
-24
-24
+69
+7
+5
+10
+18
+23
+11
+19
-3
+6
-15
+20
+11
+2
+4
+11
-14
+21
-16
-1
-16
+10
-7
+3
+34
+17
-12
+18
+8
+17
-15
+18
-6
+15
-25
-16
+1
+7
-18
+4
-3
+12
-14
-6
+19
+42
+52
+41
-4
+50
+20
+10
-14
-11
-17
+14
+7
+3
+44
-21
-63
-103
+28
+98
-21
-110
-112
+14
+28
-44
-16
+9
-6
-49
-20
+10
+5
+4
+20
+21
-29
+6
-9
-3
-11
-23
+27
-5
+39
-185
-67702
-14
-9
-11
+6
-14
+9
+15
+9
+14
-7
+5
-4
+10
+4
+19
-2
+17
+3
+7
+12
+2
-3
-6
-11
+12
-3
-5
-11
-1
+2
-10
-20
+14
-2
-8
-7
+11
+1
-17
+18
+9
-18
+5
+16
+2
-13
-16
+4
-8
-5
+3
-7
-13
-9
+10
-16
-4
-16
-13
-6
+18
-1
-19
+11
-17
+16
-19
-11
+17
-13
-1
-5
+15
-1
+14
+8
+19
+2
-19
+16
+11
+5
+11
-4
-4
+9
-8
-6
-1
+17
-12
-16
-12
-13
-6
+2
-1
+8
-17
+3
-11
-13
-17
-16
-13
+2
-6
-19
+4
-8
-13
+7
-5
-15
-17
-12
-7
-12
-19
-10
-19
+18
-1
-10
+4
+10
+2
-21
+4
-17
-14
+7
+10
+5
-17
+10
+14
+10
+17
+1
+1
+4
-15
+16
+13
-6
-11
-14
-17
-19
+4
-10
-10
+2
-10
-17
+7
+17
+4
+8
-5
-16
+8
-9
-11
-2
+1
-9
-13
+10
+4
-3
-15
+6
+3
+13
-10
+17
-1
-8
+20
+3
-1
+8
-17
+11
+13
-11
-1
+17
-4
+8
+6
+2
-6
+14
+12
-1
+13
+10
+11
+12
+4
+1
-2
-7
-13
+9
+14
+4
+18
-11
+8
+15
+17
-1
+5
+1
+1
-11
+3
-11
-9
+19
+6
+15
+8
+14
-12
-18
+10
-6
+9
-15
+17
+13
+4
-5
-13
+11
+11
+2
+7
-2
+6
+19
+3
-14
+5
+15
-17
+12
+9
+1
+13
-17
+6
-25
-20
-5
+17
+13
-6
+17
-12
-15
-4
-4
+10
+15
-18
+19
-5
-6
-3
-11
-20
-12
-17
-9
-9
+4
-9
-2
+8
-10
+1
+14
-4
-19
+6
-14
+21
-11
-3
-5
-17
+13
-3
+2
-15
-13
+1
-2
+11
-14
-11
-12
+11
+9
+4
+12
-5
+26
-7
+14
-25
-29
+23
+16
+24
-12
+27
+2
+21
+5
+5
+7
+14
-2
+4
-6
-23
-24
-10
+27
+4
+19
+20
+14
+13
+16
+1
+6
+17
+18
-5
-11
+2
+19
-11
-7
-11
+16
+17
+2
-14
+25
+14
-2
-4
+31
-9
-15
-4
+2
+8
+31
-11
+16
+21
-17
+22
-8
+5
-15
-5
+8
+16
+17
+16
-17
+19
-10
+17
-19
-3
-18
+17
+13
-14
-11
+20
-6
-16
-17
+21
-20
-6
+10
-18
-12
+8
-28
+10
+13
-14
-62
-3
+5
+116
+18
+4
-17
-2
+1
+37
+10
-8
+19
-6
-2
-10
-4
-4
-8
+17
+20
+11
-7
+9
-21
+2
-27
+10
+5
-16
-10
+5
+4
+9
+42
-1
+41
+36
+27
+13
+15
-61
-14
-27
-94
-10
-13
+21
-35
+23
+33
+13
-235
-43
+6
-55
-9
-70
-3
-6
-1
-87
-9
+8
+12
+18
+15
+18
-1
+14
-30
+39
+14
-13
+19
-122
+3
-16
+26
+20
-221
-6
+30
-51
+470
-67792
+3
+135555
"""

private const val star2Data = """+13
+9
+8
+1
-15
+10
+16
+17
+8
+8
+17
+6
+17
+18
-10
-16
+13
+15
-18
-11
-2
+7
-14
-4
+17
+18
-6
+3
-4
-2
-18
-23
+7
-5
-14
-12
+16
+16
+10
+16
-4
-21
-11
-16
-17
+3
-1
+19
+13
+18
+4
+19
-2
+13
+20
+4
-16
+6
+11
-13
-1
+19
+9
+5
+13
+14
-19
+8
-13
-5
+11
-15
-9
+5
-3
+1
-15
-1
+19
+21
+2
+1
+10
-1
+5
-6
+13
+7
-1
+7
+8
-19
+8
-13
+4
+16
+11
+7
+6
-14
+19
+3
+19
+18
+13
+19
+8
+15
+6
-9
+8
+17
-12
+2
+6
+14
-9
+8
+13
+6
+5
+19
-16
+14
-9
+7
+19
-18
-3
-15
-19
-10
+2
-17
+19
-8
-19
+7
-16
-8
+2
-18
-1
+9
+19
-17
+3
+1
+6
-14
-2
+7
-4
+14
-6
+8
+2
+5
+21
+18
+3
+8
-13
+17
-19
+13
+5
-15
+13
-8
+6
+14
+16
+13
+12
+17
+12
+17
-11
+1
+16
-19
-17
-14
-8
+3
+12
+13
+9
-1
-12
+5
-12
+15
+1
+1
+9
+8
+10
-6
+4
-2
+7
+4
-1
-2
+10
+12
+4
-6
+1
+17
+5
-10
-19
-9
-17
+18
-15
+6
+18
+19
-17
+15
-14
-2
+15
-2
-10
-18
-21
+3
-2
-16
+1
-4
+17
-18
-14
+2
+11
-18
+11
-8
-14
-10
+2
-5
-15
-8
-18
-8
+13
+19
+14
-9
+11
-5
+10
+3
+21
-20
-7
+5
+6
-3
+13
-4
+1
-3
-20
-9
-7
+10
+12
+13
+11
+12
-5
+4
+29
-9
-21
-15
+34
+38
+39
+14
+20
+3
-11
-15
+12
+22
-23
-19
-14
+6
+11
+15
-17
+12
+16
+3
+21
-10
+17
+21
+17
-3
-19
-11
-6
+20
+5
+17
+12
+8
+12
+13
+4
-1
+23
-8
+11
+17
+7
-9
-2
-11
+8
+9
+4
+19
+13
-14
+19
+17
-18
-6
+12
-19
-6
+7
+7
+9
-6
-18
+5
-6
-17
+14
-3
-16
-13
-11
+16
+19
-2
+5
+3
-7
-15
-10
+13
+8
-16
-12
-9
-19
-18
-11
-5
+13
-17
+3
+16
+25
-15
+22
-18
-17
+5
+4
-20
-9
+5
-18
+5
-23
+3
-16
+12
-4
-7
+21
-4
+7
+14
+15
-24
-24
+69
+7
+5
+10
+18
+23
+11
+19
-3
+6
-15
+20
+11
+2
+4
+11
-14
+21
-16
-1
-16
+10
-7
+3
+34
+17
-12
+18
+8
+17
-15
+18
-6
+15
-25
-16
+1
+7
-18
+4
-3
+12
-14
-6
+19
+42
+52
+41
-4
+50
+20
+10
-14
-11
-17
+14
+7
+3
+44
-21
-63
-103
+28
+98
-21
-110
-112
+14
+28
-44
-16
+9
-6
-49
-20
+10
+5
+4
+20
+21
-29
+6
-9
-3
-11
-23
+27
-5
+39
-185
-67702
-14
-9
-11
+6
-14
+9
+15
+9
+14
-7
+5
-4
+10
+4
+19
-2
+17
+3
+7
+12
+2
-3
-6
-11
+12
-3
-5
-11
-1
+2
-10
-20
+14
-2
-8
-7
+11
+1
-17
+18
+9
-18
+5
+16
+2
-13
-16
+4
-8
-5
+3
-7
-13
-9
+10
-16
-4
-16
-13
-6
+18
-1
-19
+11
-17
+16
-19
-11
+17
-13
-1
-5
+15
-1
+14
+8
+19
+2
-19
+16
+11
+5
+11
-4
-4
+9
-8
-6
-1
+17
-12
-16
-12
-13
-6
+2
-1
+8
-17
+3
-11
-13
-17
-16
-13
+2
-6
-19
+4
-8
-13
+7
-5
-15
-17
-12
-7
-12
-19
-10
-19
+18
-1
-10
+4
+10
+2
-21
+4
-17
-14
+7
+10
+5
-17
+10
+14
+10
+17
+1
+1
+4
-15
+16
+13
-6
-11
-14
-17
-19
+4
-10
-10
+2
-10
-17
+7
+17
+4
+8
-5
-16
+8
-9
-11
-2
+1
-9
-13
+10
+4
-3
-15
+6
+3
+13
-10
+17
-1
-8
+20
+3
-1
+8
-17
+11
+13
-11
-1
+17
-4
+8
+6
+2
-6
+14
+12
-1
+13
+10
+11
+12
+4
+1
-2
-7
-13
+9
+14
+4
+18
-11
+8
+15
+17
-1
+5
+1
+1
-11
+3
-11
-9
+19
+6
+15
+8
+14
-12
-18
+10
-6
+9
-15
+17
+13
+4
-5
-13
+11
+11
+2
+7
-2
+6
+19
+3
-14
+5
+15
-17
+12
+9
+1
+13
-17
+6
-25
-20
-5
+17
+13
-6
+17
-12
-15
-4
-4
+10
+15
-18
+19
-5
-6
-3
-11
-20
-12
-17
-9
-9
+4
-9
-2
+8
-10
+1
+14
-4
-19
+6
-14
+21
-11
-3
-5
-17
+13
-3
+2
-15
-13
+1
-2
+11
-14
-11
-12
+11
+9
+4
+12
-5
+26
-7
+14
-25
-29
+23
+16
+24
-12
+27
+2
+21
+5
+5
+7
+14
-2
+4
-6
-23
-24
-10
+27
+4
+19
+20
+14
+13
+16
+1
+6
+17
+18
-5
-11
+2
+19
-11
-7
-11
+16
+17
+2
-14
+25
+14
-2
-4
+31
-9
-15
-4
+2
+8
+31
-11
+16
+21
-17
+22
-8
+5
-15
-5
+8
+16
+17
+16
-17
+19
-10
+17
-19
-3
-18
+17
+13
-14
-11
+20
-6
-16
-17
+21
-20
-6
+10
-18
-12
+8
-28
+10
+13
-14
-62
-3
+5
+116
+18
+4
-17
-2
+1
+37
+10
-8
+19
-6
-2
-10
-4
-4
-8
+17
+20
+11
-7
+9
-21
+2
-27
+10
+5
-16
-10
+5
+4
+9
+42
-1
+41
+36
+27
+13
+15
-61
-14
-27
-94
-10
-13
+21
-35
+23
+33
+13
-235
-43
+6
-55
-9
-70
-3
-6
-1
-87
-9
+8
+12
+18
+15
+18
-1
+14
-30
+39
+14
-13
+19
-122
+3
-16
+26
+20
-221
-6
+30
-51
+470
-67792
+3
+135555"""
