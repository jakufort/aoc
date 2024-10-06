use std::fs::File;
use std::io;
use std::io::BufRead;
use std::path::Path;

pub fn lines<P>(filename: P) -> Vec<String> where P: AsRef<Path> {
    let mut all_lines: Vec<String> = Vec::new();
    if let Ok(lines) = read_lines(filename) {
        for maybe_line in lines {
            if let Ok(line) = maybe_line {
                all_lines.push(line)
            }
        }
    }
    return all_lines
}

fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>> where P: AsRef<Path> {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}