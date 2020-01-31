#name: Qi Luo
#Anumber: A02274095


NFA_DELTA_01 = {}
NFA_DELTA_01[('q0', '0')] = set(['q0'])
NFA_DELTA_01[('q0', '1')] = set(['q0', 'q1'])
NFA_DELTA_01[('q1', '0')] = set(['q2'])
NFA_DELTA_01[('q1', '1')] = set(['q2'])
NFA_01 = (set(['q0', 'q1', 'q2']),
          set(['0', '1']),
          NFA_DELTA_01,
          'q0',
          set(['q2']))


def display_dfa(dfa):
    print('STATES:{}'.format(dfa[0]))
    print('SIGMA:{}'.format(dfa[1]))
    print('START STATE:{}'.format(dfa[3]))
    print('DELTA:')

    i = 0
    for move in dfa[2]:
        from_state, sym, to_state = move
        print('{}) d({}, {}) = {}'.format(i, from_state, sym, to_state))
        i = i + 1

    # for i, move in enumerate(dfa[2]):
    #     from_state, sym, to_state = move
    #     print('{}) d({}, {}) = {}'.format(i, from_state, sym, to_state))

    print('FINAL STATES: {}'.format(dfa[4]))


def nfa_to_dfa(nfa):
    dfa_states = []
    dfa_sigma = nfa[1]
    dfa_start_state = set([nfa[3]])
    dfa_delta = []
    dfa_final_states = []

    dfa_states.append(dfa_start_state)

    queue = [dfa_start_state]

    while len(queue) != 0:
        from_state = queue.pop()

        for path in dfa_sigma:
            to_state = set()

            for st in from_state:
                key = (st, path)
                if key in nfa[2]:
                    sts = nfa[2][key]
                    for stt in sts:
                        to_state.add(stt)

            exist_move = False
            for fs, p, ts in dfa_delta:
                if fs == from_state and p == path and ts == to_state:
                    exist_move = True

            if not exist_move:
                dfa_delta.append((from_state, path, to_state))

            exist_state = False
            for state in dfa_states:
                if to_state == state:
                    exist_state = True

            if not exist_state:
                dfa_states.append(to_state)
                queue.insert(0,to_state)

    nfa_final_states = nfa[4]

    for dfa_st in dfa_states:
        obtain = len(dfa_st.intersection(nfa_final_states))
        if obtain > 0:
            dfa_final_states.append(dfa_st)

    return dfa_states, dfa_sigma, dfa_delta, dfa_start_state, dfa_final_states


dfa = nfa_to_dfa(NFA_01)
display_dfa(dfa)
