package year2017

import (
	"fmt"
	"math"
	"strconv"
	"strings"
)

// Day2 - Completes the challenges, parts 1 & 2, for http://adventofcode.com/2017/day/2
func Day2() {
	fmt.Printf("Day 2:")
	fmt.Println()
	fmt.Printf("\tPart 1 answer: %d", day2Part1(day2Input))
	fmt.Println()
	fmt.Printf("\tPart 2 answer: %d", day2Part2(day2Input))
	fmt.Println()
}

var day2Input = []string{
	"104	240	147	246	123	175	372	71	116	230	260	118	202	270	277	292",
	"740	755	135	205	429	822	844	90	828	115	440	805	526	91	519	373",
	"1630	991	1471	1294	52	1566	50	1508	1367	1489	55	547	342	512	323	51",
	"1356	178	1705	119	1609	1409	245	292	1434	694	405	1692	247	193	1482	1407",
	"2235	3321	3647	212	1402	3711	3641	1287	2725	692	1235	3100	123	144	104	101",
	"1306	1224	1238	186	751	734	1204	1275	366	149	1114	166	1118	239	153	943",
	"132	1547	1564	512	2643	2376	2324	2159	1658	107	1604	145	2407	131	2073	1878",
	"1845	91	1662	108	92	1706	1815	1797	1728	1150	1576	83	97	547	1267	261",
	"78	558	419	435	565	107	638	173	93	580	338	52	633	256	377	73",
	"1143	3516	4205	3523	148	401	3996	3588	300	1117	2915	1649	135	134	182	267",
	"156	2760	1816	2442	2985	990	2598	1273	167	821	138	141	2761	2399	1330	1276",
	"3746	3979	2989	161	4554	156	3359	173	3319	192	3707	264	762	2672	4423	2924",
	"3098	4309	4971	5439	131	171	5544	595	154	571	4399	4294	160	6201	4329	5244",
	"728	249	1728	305	2407	239	691	2241	2545	1543	55	2303	1020	753	193	1638",
	"260	352	190	877	118	77	1065	1105	1085	1032	71	87	851	56	1161	667",
	"1763	464	182	1932	1209	640	545	931	1979	197	1774	174	2074	1800	939	161",
}

func day2Part1(input []string) int {
	var sum int

	for _, e := range input {
		var max, min int
		nums := strings.Split(e, "\t")

		for _, sNum := range nums {
			num, _ := strconv.Atoi(sNum)

			// TODO : Fix for just 2 numbers and 0s
			if num > max {
				max = num
			}
			if num < min || min == 0 {
				min = num
			}
		}

		sum += max - min
	}

	return sum
}

func day2Part2(input []string) int {
	var sum int

	for _, e := range input {
		sNums := strings.Split(e, "\t")
		sum += findEvenDividers(toInts(sNums))
	}

	return sum
}

func findEvenDividers(nums []int) int {
	length := len(nums)

	for i := 0; i < length-1; i++ {
		n1 := nums[i]
		n1f := float64(n1)
		for x := i + 1; x < length; x++ {
			n2 := nums[x]
			n2f := float64(n2)

			if n1 > n2 {
				if math.Mod(n1f, n2f) < 0.00005 {
					return n1 / n2
				}
			} else {
				if math.Mod(n2f, n1f) < 0.00005 {
					return n2 / n1
				}
			}
		}
	}

	return 0
}

func toInts(sNums []string) []int {
	nums := make([]int, len(sNums))

	for i, sNum := range sNums {
		num, _ := strconv.Atoi(sNum)
		nums[i] = num
	}

	return nums
}
