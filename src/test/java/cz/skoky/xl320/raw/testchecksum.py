
# 255 - ((self.id + self.length + self.instruction +
#             sum(self.parameters)) % 256))

print (255 - ((0xFE + 2 + 1 + 0) % 256))

print (255 - ((1 + 5 + 3 + 0xC +0x64 +0xaa) % 256))

# ffff0105031efa01db

print (255 - ((1 + 5 + 3 + 0x1e +0xfa +0x1) % 256))