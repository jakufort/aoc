require 'minitest/autorun'

def extract_commands(input)
  input.scan(/(mul\([0-9]{1,3},[0-9]{1,3}\))/).flatten
end

def multiply(command)
  m = command.match(/([0-9]{1,3}),([0-9]{1,3})/)
  m[1].to_i * m[2].to_i
end

input = File.open("input/day3").read.gsub!("\n", "")

commands = extract_commands(input)

sum = commands.map {|cmd| multiply(cmd) }.sum

puts "part1: #{sum}"

class Day3Test < Minitest::Test 
  def test_multiply_command
    assert_equal 5, multiply("mul(5,1)")
    assert_equal 20, multiply("mul(10,2)")
  end

  def test_extract_commands
    input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    
    assert_equal ["mul(2,4)", "mul(5,5)", "mul(11,8)", "mul(8,5)"], extract_commands(input)
  end
end