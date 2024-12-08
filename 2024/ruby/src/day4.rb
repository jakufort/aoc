require 'minitest/autorun'

MAS = [
  [[-1, -1], [-2, -2], [-3, -3]],
  [[-1, 0], [-2, 0], [-3, 0]],
  [[-1, 1], [-2, 2], [-3, 3]],
  [[0, 1], [0, 2], [0, 3]],
  [[1, 1], [2, 2], [3, 3]],
  [[1, 0], [2, 0], [3, 0]],
  [[1, -1], [2, -2], [3, -3]],
  [[0, -1], [0, -2], [0, -3]]
]

def matrix(input)
  input.split("\n").map {|line| line.chars }
end

def absolute_position(starting, relative)
  [starting[0] + relative[0], starting[1] + relative[1]] 
end

def cell(matrix, pos)
  return nil if pos[0] < 0 or pos[1] < 0
  (matrix[pos[0]] || [])[pos[1]]
end

def count_xmas_occurences(matrix, x_pos)
  count = 0
  for m, a, s in MAS
    m_abs = absolute_position(x_pos, m)
    a_abs = absolute_position(x_pos, a)
    s_abs = absolute_position(x_pos, s)
    if cell(matrix, m_abs) == 'M' and cell(matrix, a_abs) == 'A' and cell(matrix, s_abs) == 'S'
      count += 1
    end
  end
  count
end

def count_xmas(matrix)
  counter = 0
  for y in 0..(matrix.size - 1)
    for x in 0..(matrix[y].size - 1)
      x_pos = [y, x]
      if cell(matrix, x_pos) == 'X'
        counter += count_xmas_occurences(matrix, x_pos)
      end
    end
  end
  counter
end

def count_cross_mas(matrix)
  counter = 0
  for y in 0..(matrix.size - 1)
    for x in 0..(matrix[y].size - 1)
      x_pos = [y, x]
      if cell(matrix, x_pos) == 'A'
        left_top = cell(matrix, absolute_position(x_pos, [-1, -1]))
        right_top = cell(matrix, absolute_position(x_pos, [-1, 1]))
        bottom_left = cell(matrix, absolute_position(x_pos, [1, -1]))
        bottom_right = cell(matrix, absolute_position(x_pos, [1, 1]))

        if left_top == 'M'
          if right_top == 'M'
            if bottom_left == 'S' and bottom_right == 'S'
              counter += 1
            end
          elsif right_top == 'S'
            if bottom_left == 'M' and bottom_right == 'S'
              counter += 1
            end
          end
        elsif left_top == 'S'
          if right_top == 'M'
            if bottom_left == 'S' and bottom_right == 'M'
              counter += 1
            end
          elsif right_top == 'S'
            if bottom_left == 'M' and bottom_right == 'M'
              counter += 1
            end
          end
        end
      end
    end
  end
  counter
end

input = File.open("input/day4").read

matrix = matrix(input)

puts "part 1: #{count_xmas(matrix)}"
puts "part 2: #{count_cross_mas(matrix)}"

class Day3Test < Minitest::Test 
  def test_input_reading
    input = <<~INPUT
      MMM
      MSA
      AMX
    INPUT

    assert_equal [['M', 'M', 'M'], ['M', 'S', 'A'], ['A', 'M', 'X']], matrix(input)
  end

  def test_xmas_count
    input = <<~INPUT
      MMMSXXMASM
      MSAMXMSMSA
      AMXSXMAAMM
      MSAMASMSMX
      XMASAMXAMM
      XXAMMXXAMA
      SMSMSASXSS
      SAXAMASAAA
      MAMMMXMMMM
      MXMXAXMASX
    INPUT

    assert_equal 18, count_xmas(matrix(input))
  end

  def test_count_cross
    input = <<~INPUT
      MMMSXXMASM
      MSAMXMSMSA
      AMXSXMAAMM
      MSAMASMSMX
      XMASAMXAMM
      XXAMMXXAMA
      SMSMSASXSS
      SAXAMASAAA
      MAMMMXMMMM
      MXMXAXMASX
    INPUT

    assert_equal 9, count_cross_mas(matrix(input))
  end

end