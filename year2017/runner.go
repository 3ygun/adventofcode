package year2017

import "fmt"

type dayFunc func()

var dayToRun = map[string]dayFunc{
	"1": Day1,
}

// Year of this runner
const Year = "2017"

func Run(day string) {
	if v, ok := dayToRun[day]; ok {
		v()
	} else {
		fmt.Printf("%s is valid day in %s", day, Year)
		fmt.Println()
	}
}

func RunAll() {
	for _, v := range dayToRun {
		v()
	}
}
