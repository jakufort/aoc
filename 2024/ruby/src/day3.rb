require 'minitest/autorun'

def extract_commands(input)
  input.scan(/(mul\([0-9]{1,3},[0-9]{1,3}\)|do\(\)|don't\(\))/).flatten
end

def multiply(command)
  if !command.start_with?("mul")
    return 0
  end
  m = command.match(/([0-9]{1,3}),([0-9]{1,3})/)
  m[1].to_i * m[2].to_i
end

def run_multiplications(multiplications)
  multiplications.map {|cmd| multiply(cmd) }.sum
end

def filter_out_disabled_multiplications(commands)
  multiplications = []
  skip = false
  for command in commands
    if command.start_with?('mul')
      if !skip
        multiplications.push(command)
      end
    elsif command.start_with?("don't()")
      skip = true
    elsif command.start_with?("do()")
      skip = false
    end
  end
  multiplications
end

input = File.open("input/day3").read.gsub!("\n", "")

commands = extract_commands(input)

puts "part1: #{run_multiplications(commands)}"

puts "part2: #{run_multiplications(filter_out_disabled_multiplications(commands))}"

class Day3Test < Minitest::Test 
  def test_multiply_command
    assert_equal 5, multiply("mul(5,1)")
    assert_equal 20, multiply("mul(10,2)")
  end

  def test_extract_commands
    input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    
    assert_equal ["mul(2,4)", "mul(5,5)", "mul(11,8)", "mul(8,5)"], extract_commands(input)
  end

  def test_extract_multiple_commands
    input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

    assert_equal ["mul(2,4)", "don't()", "mul(5,5)", "mul(11,8)", "do()", "mul(8,5)"], extract_commands(input)
  end

  def test_multiplications_filtering
    input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"

    assert_equal ["mul(2,4)", "mul(8,5)"], filter_out_disabled_multiplications(extract_commands(input))
  end
end