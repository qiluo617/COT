class Mod3DFA:

    def __init__(self):
        self.q0 = 0
        self.q1 = 1
        self.q2 = 2
        self.q3 = 3

    def processString(self, str):

        curr_state = self.q0

        for i in range(0, len(str)):

            curr_path = int(str[i])

            if curr_state == self.q0:
                if curr_path == 0:
                    curr_state = self.q0
                elif curr_path == 1:
                    curr_state = self.q1
                else:
                    curr_state = self.q3

            elif curr_state == self.q1:
                if curr_path == 0:
                    curr_state = self.q2
                elif curr_path == 1:
                    curr_state = self.q0
                else:
                    curr_state = self.q3

            elif curr_state == self.q2:
                if curr_path == 0:
                    curr_state = self.q1
                elif curr_path == 1:
                    curr_state = self.q2
                else:
                    curr_state = self.q3

            elif curr_state == self.q3:
                if curr_path == 0:
                    curr_state = self.q3
                elif curr_path == 1:
                    curr_state = self.q3

        # print curr_state
        if curr_state == self.q0:
            return True
        else:
            return False


mod3DFA = Mod3DFA()
print mod3DFA.processString("0101010")
