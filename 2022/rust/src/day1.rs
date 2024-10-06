use crate::utils;

pub fn solution() {
    part_1()
}

fn part_1() {
    let lines = utils::lines("./resources/day1/input");
    let calories = calories_list(lines);
    println!("Day 1");
    println!("part1: {}", calories.first().unwrap());
    println!("part2: {}", calories.into_iter().take(3).sum::<i32>())
}

fn calories_list(lines: Vec<String>) -> Vec<i32> {
    let mut elves: Vec<i32> = Vec::new();
    let mut current_elf: i32 = 0;
    for line in lines {
        if line != "" {
            let calories = line.parse::<i32>().unwrap_or(0);
            current_elf += calories
        } else {
            elves.push(current_elf);
            current_elf = 0
        }
    }
    elves.push(current_elf);
    elves.sort_unstable();
    elves.reverse();
    return elves
}


#[cfg(test)]
mod tests {
    use crate::day1::calories_list;

    #[test]
    fn sums_elves_calories() {
        let input: Vec<String> = vec![str("1"), str("2"), str(""), str("2")];
        let result = calories_list(input);
        assert_eq!(result, vec![3, 2])
    }

    #[test]
    fn sorts_descending() {
        let input = vec![str("1"), str("2"), str(""), str("4"), str(""), str("3"), str("3")];
        let result = calories_list(input);
        assert_eq!(result, vec![6, 4, 3])
    }

    fn str(str: &str) -> String {
        String::from(str)
    }
}