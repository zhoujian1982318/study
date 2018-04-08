-- KEYS[1] 'str:1'
-- KEYS[2] 'str:2'

-- ARGV[1]  timestamp

local function getValue(key)
     return redis.call("GET", key)
end
local result = {}
local ts = ARGV[1]
local value1 = getValue(KEYS[1])
local value2 = getValue(KEYS[2])
result[1] = {}
result[1][1] = ts
result[1][2] = value1
result[1][3] = value2

return result
