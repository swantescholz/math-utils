from decimal import Decimal

AAs = ["A", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "Y"]
AA_mass_table = {"A": Decimal("71.03711"), "C": Decimal("103.00919"), "D": Decimal("115.02694"), "E": Decimal("129.04259"), "F": Decimal("147.06841"),
                 "G": Decimal("57.02146"), "H": Decimal("137.05891"), "I": Decimal("113.08406"),
                 "K": Decimal("128.09496"), "L": Decimal("113.08406"), "M": Decimal("131.04049"), "N": Decimal("114.04293"), "P": Decimal("97.05276"),
                 "Q": Decimal("128.05858"), "R": Decimal("156.10111"), "S": Decimal("87.03203"),
                 "T": Decimal("101.04768"), "V": Decimal("99.06841"), "W": Decimal("186.07931"), "Y": Decimal("163.06333")}

_BLOSUM62 = [[4, 0, -2, -1, -2, 0, -2, -1, -1, -1, -1, -2, -1, -1, -1, 1, 0, 0, -3, -2],
             [0, 9, -3, -4, -2, -3, -3, -1, -3, -1, -1, -3, -3, -3, -3, -1, -1, -1, -2, -2],
             [-2, -3, 6, 2, -3, -1, -1, -3, -1, -4, -3, 1, -1, 0, -2, 0, -1, -3, -4, -3],
             [-1, -4, 2, 5, -3, -2, 0, -3, 1, -3, -2, 0, -1, 2, 0, 0, -1, -2, -3, -2],
             [-2, -2, -3, -3, 6, -3, -1, 0, -3, 0, 0, -3, -4, -3, -3, -2, -2, -1, 1, 3],
             [0, -3, -1, -2, -3, 6, -2, -4, -2, -4, -3, 0, -2, -2, -2, 0, -2, -3, -2, -3],
             [-2, -3, -1, 0, -1, -2, 8, -3, -1, -3, -2, 1, -2, 0, 0, -1, -2, -3, -2, 2],
             [-1, -1, -3, -3, 0, -4, -3, 4, -3, 2, 1, -3, -3, -3, -3, -2, -1, 3, -3, -1],
             [-1, -3, -1, 1, -3, -2, -1, -3, 5, -2, -1, 0, -1, 1, 2, 0, -1, -2, -3, -2],
             [-1, -1, -4, -3, 0, -4, -3, 2, -2, 4, 2, -3, -3, -2, -2, -2, -1, 1, -2, -1],
             [-1, -1, -3, -2, 0, -3, -2, 1, -1, 2, 5, -2, -2, 0, -1, -1, -1, 1, -1, -1],
             [-2, -3, 1, 0, -3, 0, 1, -3, 0, -3, -2, 6, -2, 0, 0, 1, 0, -3, -4, -2],
             [-1, -3, -1, -1, -4, -2, -2, -3, -1, -3, -2, -2, 7, -1, -2, -1, -1, -2, -4, -3],
             [-1, -3, 0, 2, -3, -2, 0, -3, 1, -2, 0, 0, -1, 5, 1, 0, -1, -2, -2, -1],
             [-1, -3, -2, 0, -3, -2, 0, -3, 2, -2, -1, 0, -2, 1, 5, -1, -1, -3, -3, -2],
             [1, -1, 0, 0, -2, 0, -1, -2, 0, -2, -1, 1, -1, 0, -1, 4, 1, -2, -3, -2],
             [0, -1, -1, -1, -2, -2, -2, -1, -1, -1, -1, 0, -1, -1, -1, 1, 5, 0, -2, -2],
             [0, -1, -3, -2, -1, -3, -3, 3, -2, 1, 1, -3, -2, -2, -3, -2, 0, 4, -3, -1],
             [-3, -2, -4, -3, 1, -2, -2, -3, -3, -2, -1, -4, -4, -2, -3, -3, -2, -3, 11, 2],
             [-2, -2, -3, -2, 3, -3, 2, -1, -2, -1, -1, -2, -3, -1, -2, -2, -2, -1, 2, 7]]

_PAM250 = [[2, -2, 0, 0, -3, 1, -1, -1, -1, -2, -1, 0, 1, 0, -2, 1, 1, 0, -6, -3],
           [-2, 12, -5, -5, -4, -3, -3, -2, -5, -6, -5, -4, -3, -5, -4, 0, -2, -2, -8, 0],
           [0, -5, 4, 3, -6, 1, 1, -2, 0, -4, -3, 2, -1, 2, -1, 0, 0, -2, -7, -4],
           [0, -5, 3, 4, -5, 0, 1, -2, 0, -3, -2, 1, -1, 2, -1, 0, 0, -2, -7, -4],
           [-3, -4, -6, -5, 9, -5, -2, 1, -5, 2, 0, -3, -5, -5, -4, -3, -3, -1, 0, 7],
           [1, -3, 1, 0, -5, 5, -2, -3, -2, -4, -3, 0, 0, -1, -3, 1, 0, -1, -7, -5],
           [-1, -3, 1, 1, -2, -2, 6, -2, 0, -2, -2, 2, 0, 3, 2, -1, -1, -2, -3, 0],
           [-1, -2, -2, -2, 1, -3, -2, 5, -2, 2, 2, -2, -2, -2, -2, -1, 0, 4, -5, -1],
           [-1, -5, 0, 0, -5, -2, 0, -2, 5, -3, 0, 1, -1, 1, 3, 0, 0, -2, -3, -4],
           [-2, -6, -4, -3, 2, -4, -2, 2, -3, 6, 4, -3, -3, -2, -3, -3, -2, 2, -2, -1],
           [-1, -5, -3, -2, 0, -3, -2, 2, 0, 4, 6, -2, -2, -1, 0, -2, -1, 2, -4, -2],
           [0, -4, 2, 1, -3, 0, 2, -2, 1, -3, -2, 2, 0, 1, 0, 1, 0, -2, -4, -2],
           [1, -3, -1, -1, -5, 0, 0, -2, -1, -3, -2, 0, 6, 0, 0, 1, 0, -1, -6, -5],
           [0, -5, 2, 2, -5, -1, 3, -2, 1, -2, -1, 1, 0, 4, 1, -1, -1, -2, -5, -4],
           [-2, -4, -1, -1, -4, -3, 2, -2, 3, -3, 0, 0, 0, 1, 6, 0, -1, -2, 2, -4],
           [1, 0, 0, 0, -3, 1, -1, -1, 0, -3, -2, 1, 1, -1, 0, 2, 1, -1, -2, -3],
           [1, -2, 0, 0, -3, 0, -1, 0, 0, -2, -1, 0, 0, -1, -1, 1, 3, 0, -5, -3],
           [0, -2, -2, -2, -1, -1, -2, 4, -2, 2, 2, -2, -1, -2, -2, -1, 0, 4, -6, -2],
           [-6, -8, -7, -7, 0, -7, -3, -5, -3, -2, -4, -4, -6, -5, 2, -2, -5, -6, 17, 0],
           [-3, 0, -4, -4, 7, -5, 0, -1, -4, -1, -2, -2, -5, -4, -4, -3, -3, -2, 0, 10]]

BLOSUM62 = dict((a, dict((b, _BLOSUM62[i][j]) for j, b in enumerate(AAs))) for i, a in enumerate(AAs))
PAM250 = dict((a, dict((b, _PAM250[i][j]) for j, b in enumerate(AAs))) for i, a in enumerate(AAs))


DNA_stop_codons = {"TGA", "TAA", "TAG"}
DNA_start_codon = "ATG"

DNA_codon_table = dict(
	[("TTT", "F"), ("TTC", "F"), ("TTA", "L"), ("TTG", "L"), ("TCT", "S"), ("TCC", "S"), ("TCA", "S"), ("TCG", "S"), ("TAT", "Y"), ("TAC", "Y"),
	 ("TGT", "C"), ("TGC", "C"), ("TGG", "W"), ("CTT", "L"), ("CTC", "L"), ("CTA", "L"), ("CTG", "L"), ("CCT", "P"), ("CCC", "P"), ("CCA", "P"),
	 ("CCG", "P"), ("CAT", "H"), ("CAC", "H"), ("CAA", "Q"), ("CAG", "Q"), ("CGT", "R"), ("CGC", "R"), ("CGA", "R"), ("CGG", "R"), ("ATT", "I"),
	 ("ATC", "I"), ("ATA", "I"), ("ATG", "M"), ("ACT", "T"), ("ACC", "T"), ("ACA", "T"), ("ACG", "T"), ("AAT", "N"), ("AAC", "N"), ("AAA", "K"),
	 ("AAG", "K"), ("AGT", "S"), ("AGC", "S"), ("AGA", "R"), ("AGG", "R"), ("GTT", "V"), ("GTC", "V"), ("GTA", "V"), ("GTG", "V"), ("GCT", "A"),
	 ("GCC", "A"), ("GCA", "A"), ("GCG", "A"), ("GAT", "D"), ("GAC", "D"), ("GAA", "E"), ("GAG", "E"), ("GGT", "G"), ("GGC", "G"), ("GGA", "G"),
	 ("GGG", "G")])

# UAA UAG UGA Stop
rna_to_protein_dict = dict(
	[("UUU", "F"), ("UUC", "F"), ("UUA", "L"), ("UUG", "L"), ("UCU", "S"), ("UCC", "S"), ("UCA", "S"), ("UCG", "S"), ("UAU", "Y"), ("UAC", "Y"),
	 ("UGU", "C"), ("UGC", "C"), ("UGG", "W"), ("CUU", "L"), ("CUC", "L"), ("CUA", "L"), ("CUG", "L"), ("CCU", "P"), ("CCC", "P"), ("CCA", "P"),
	 ("CCG", "P"), ("CAU", "H"), ("CAC", "H"), ("CAA", "Q"), ("CAG", "Q"), ("CGU", "R"), ("CGC", "R"), ("CGA", "R"), ("CGG", "R"), ("AUU", "I"),
	 ("AUC", "I"), ("AUA", "I"), ("AUG", "M"), ("ACU", "T"), ("ACC", "T"), ("ACA", "T"), ("ACG", "T"), ("AAU", "N"), ("AAC", "N"), ("AAA", "K"),
	 ("AAG", "K"), ("AGU", "S"), ("AGC", "S"), ("AGA", "R"), ("AGG", "R"), ("GUU", "V"), ("GUC", "V"), ("GUA", "V"), ("GUG", "V"), ("GCU", "A"),
	 ("GCC", "A"), ("GCA", "A"), ("GCG", "A"), ("GAU", "D"), ("GAC", "D"), ("GAA", "E"), ("GAG", "E"), ("GGU", "G"), ("GGC", "G"), ("GGA", "G"),
	 ("GGG", "G")])