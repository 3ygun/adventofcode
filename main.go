package main

import (
	"flag"
	"fmt"

	"github.com/3ygun/adventofcode/year2017"
)

func main() {
	year := flag.String("year", "", "a string of the year")
	day := flag.String("day", "", "a string of the day")
	all := flag.Bool("all", false, "run all days")
	flag.Parse()

	runWith(*year, *day, *all)
}

func runWith(year string, day string, all bool) {
	// fmt.Printf("year: %s\nday: %s\nall: %t\n\n", year, day, all)

	if year != "" && (all || day != "") {
		fmt.Println()
		runYear(year, day, all)
		fmt.Println()
	} else {
		flag.PrintDefaults()
	}
}

func runYear(year string, day string, all bool) {
	switch year {
	case year2017.Year:
		if all {
			year2017.RunAll()
		} else {
			year2017.Run(day)
		}
	}
}
