// #LTLProperty: [](started(ERC20Burnable.burnFrom(from,amount), this._balances[from] > 0 && this._balances[from] < 0x10000000000000000000000000000000000000000000000000000000000000000 && amount >= 0 && amount <= this._balances[from] && this._allowances[from][msg.sender] > this._balances[from] && this._allowances[from][msg.sender] < 0x10000000000000000000000000000000000000000000000000000000000000000 && this._balances[from] - amount >= 0 && this._totalSupply - amount >= 0) ==> <>(finished(ERC20Burnable.burnFrom(from,amount), this._allowances[from][msg.sender] == old(this._allowances[from][msg.sender]) - amount && this._name == old(this._name) && this._symbol == old(this._symbol) && this._decimals == old(this._decimals)) || reverted(ERC20Burnable.burnFrom)))

type Ref = int;
type ContractName = int;
const unique null: Ref;
const unique IERC20: ContractName;
const unique Context: ContractName;
const unique SafeMath: ContractName;
const unique ERC20: ContractName;
const unique ERC20Detailed: ContractName;
const unique ERC20Burnable: ContractName;
const unique BoltToken: ContractName;
function {:smtdefined "x"} ConstantToRef(x: int) returns (ret: Ref);
function BoogieRefToInt(x: Ref) returns (ret: int);
function {:bvbuiltin "mod"} modBpl(x: int, y: int) returns (ret: int);
function keccak256(x: int) returns (ret: int);
function abiEncodePacked1(x: int) returns (ret: int);
function _SumMapping_VeriSol(x: [Ref]int) returns (ret: int);
function abiEncodePacked2(x: int, y: int) returns (ret: int);
function abiEncodePacked1R(x: Ref) returns (ret: int);
function abiEncodePacked2R(x: Ref, y: int) returns (ret: int);
function {:smtdefined "((as const (Array Int Int)) 0)"} zeroRefintArr() returns (ret: [Ref]int);
function {:smtdefined "((as const (Array Int (Array Int Int))) ((as const (Array Int Int)) 0))"} zeroRefRefintArr() returns (ret: [Ref][Ref]int);
function nonlinearMul(x: int, y: int) returns (ret: int);
function nonlinearDiv(x: int, y: int) returns (ret: int);
function nonlinearPow(x: int, y: int) returns (ret: int);
function nonlinearMod(x: int, y: int) returns (ret: int);
var Balance: [Ref]int;
var DType: [Ref]ContractName;
var Alloc: [Ref]bool;
var balance_ADDR: [Ref]int;
var M_Ref_int: [Ref][Ref]int;
var sum__balances0: [Ref]int;
var alloc__allowances_ERC20_lvl0: [Ref][Ref]bool;
function {:smtdefined "((as const (Array Int Int)) 0)"} zeroRefRefArr() returns (ret: [Ref]Ref);
var M_Ref_Ref: [Ref][Ref]Ref;
var sum__allowances1: [Ref]int;
var Length: [Ref]int;
var revert: bool;
var gas: int;
var now: int;
procedure {:inline 1} FreshRefGenerator__success() returns (newRef: Ref);
modifies Alloc;
procedure {:inline 1} _msgSender_Context__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: Ref);
procedure {:inline 1} add~uint256~uint256_SafeMath__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s107: int, b_s107: int) returns (__ret_0_: int);
modifies revert;
procedure {:inline 1} sub~uint256~uint256_SafeMath__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s123: int, b_s123: int) returns (__ret_0_: int);
modifies revert;
procedure {:inline 1} sub~uint256~uint256~string_SafeMath__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s150: int, b_s150: int, errorMessage_s150: int) returns (__ret_0_: int);
modifies revert;
var {:access "this._balances[i1]=_balances_ERC20[this][i1]"} {:sum "sum(this._balances)=sum__balances0[this]"} _balances_ERC20: [Ref][Ref]int;
var {:access "this._allowances[i1][i2]=_allowances_ERC20[this][i1][i2]"} {:sum "sum(this._allowances)=sum__allowances1[this]"} _allowances_ERC20: [Ref][Ref][Ref]int;
var {:access "this._totalSupply=_totalSupply_ERC20[this]"} _totalSupply_ERC20: [Ref]int;
procedure {:public} {:inline 1} totalSupply_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
implementation totalSupply_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
call __ret_0_ := totalSupply_ERC20__fail(this, msgsender_MSG, msgvalue_MSG);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := totalSupply_ERC20__success(this, msgsender_MSG, msgvalue_MSG);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} balanceOf~address_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s306: Ref) returns (__ret_0_: int);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
implementation balanceOf~address_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s306: Ref) returns (__ret_0_: int)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
call __ret_0_ := balanceOf~address_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, account_s306);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := balanceOf~address_ERC20__success(this, msgsender_MSG, msgvalue_MSG, account_s306);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} transfer~address~uint256_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, recipient_s325: Ref, amount_s325: int) returns (__ret_0_: bool);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__balances0;
modifies _balances_ERC20;
implementation transfer~address~uint256_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, recipient_s325: Ref, amount_s325: int) returns (__ret_0_: bool)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
call __ret_0_ := transfer~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, recipient_s325, amount_s325);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := transfer~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, recipient_s325, amount_s325);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} allowance~address~address_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, owner_s341: Ref, spender_s341: Ref) returns (__ret_0_: int);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
implementation allowance~address~address_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, owner_s341: Ref, spender_s341: Ref) returns (__ret_0_: int)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
call __ret_0_ := allowance~address~address_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, owner_s341, spender_s341);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := allowance~address~address_ERC20__success(this, msgsender_MSG, msgvalue_MSG, owner_s341, spender_s341);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} approve~address~uint256_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s360: Ref, amount_s360: int) returns (__ret_0_: bool);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__allowances1;
modifies _allowances_ERC20;
implementation approve~address~uint256_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s360: Ref, amount_s360: int) returns (__ret_0_: bool)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
call __ret_0_ := approve~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, spender_s360, amount_s360);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := approve~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, spender_s360, amount_s360);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} transferFrom~address~address~uint256_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, sender_s396: Ref, recipient_s396: Ref, amount_s396: int) returns (__ret_0_: bool);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__balances0;
modifies _balances_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
implementation transferFrom~address~address~uint256_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, sender_s396: Ref, recipient_s396: Ref, amount_s396: int) returns (__ret_0_: bool)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
call __ret_0_ := transferFrom~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := transferFrom~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} increaseAllowance~address~uint256_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s423: Ref, addedValue_s423: int) returns (__ret_0_: bool);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__allowances1;
modifies _allowances_ERC20;
implementation increaseAllowance~address~uint256_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s423: Ref, addedValue_s423: int) returns (__ret_0_: bool)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
call __ret_0_ := increaseAllowance~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, spender_s423, addedValue_s423);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := increaseAllowance~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, spender_s423, addedValue_s423);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} decreaseAllowance~address~uint256_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s451: Ref, subtractedValue_s451: int) returns (__ret_0_: bool);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__allowances1;
modifies _allowances_ERC20;
implementation decreaseAllowance~address~uint256_ERC20(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s451: Ref, subtractedValue_s451: int) returns (__ret_0_: bool)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
call __ret_0_ := decreaseAllowance~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, spender_s451, subtractedValue_s451);
assume ((revert) || ((gas) < (0)));
} else {
call __ret_0_ := decreaseAllowance~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, spender_s451, subtractedValue_s451);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:inline 1} _transfer~address~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, sender_s508: Ref, recipient_s508: Ref, amount_s508: int);
modifies revert;
modifies sum__balances0;
modifies _balances_ERC20;
procedure {:inline 1} _burn~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s595: Ref, amount_s595: int);
modifies revert;
modifies sum__balances0;
modifies _balances_ERC20;
modifies _totalSupply_ERC20;
procedure {:inline 1} _approve~address~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, owner_s637: Ref, spender_s637: Ref, amount_s637: int);
modifies revert;
modifies sum__allowances1;
modifies _allowances_ERC20;
procedure {:inline 1} _burnFrom~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s666: Ref, amount_s666: int);
modifies revert;
modifies sum__balances0;
modifies _balances_ERC20;
modifies _totalSupply_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
var {:access "this._name=_name_ERC20Detailed[this]"} _name_ERC20Detailed: [Ref]int;
var {:access "this._symbol=_symbol_ERC20Detailed[this]"} _symbol_ERC20Detailed: [Ref]int;
var {:access "this._decimals=_decimals_ERC20Detailed[this]"} _decimals_ERC20Detailed: [Ref]int;
procedure {:public} {:inline 1} burn~uint256_ERC20Burnable(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, amount_s738: int);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__balances0;
modifies _balances_ERC20;
modifies _totalSupply_ERC20;
implementation burn~uint256_ERC20Burnable(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, amount_s738: int)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
call burn~uint256_ERC20Burnable__fail(this, msgsender_MSG, msgvalue_MSG, amount_s738);
assume ((revert) || ((gas) < (0)));
} else {
call burn~uint256_ERC20Burnable__success(this, msgsender_MSG, msgvalue_MSG, amount_s738);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:public} {:inline 1} burnFrom~address~uint256_ERC20Burnable(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s751: Ref, amount_s751: int);
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__balances0;
modifies _balances_ERC20;
modifies _totalSupply_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
implementation burnFrom~address~uint256_ERC20Burnable(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s751: Ref, amount_s751: int)
{
var __exception: bool;
havoc __exception;
revert := false;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
call burnFrom~address~uint256_ERC20Burnable__fail(this, msgsender_MSG, msgvalue_MSG, account_s751, amount_s751);
assume ((revert) || ((gas) < (0)));
} else {
call burnFrom~address~uint256_ERC20Burnable__success(this, msgsender_MSG, msgvalue_MSG, account_s751, amount_s751);
assume ((!(revert)) && ((gas) >= (0)));
}
}

procedure {:inline 1} FallbackDispatch__success(from: Ref, to: Ref, amount: int);
procedure {:inline 1} Fallback_UnknownType__success(from: Ref, to: Ref, amount: int);
procedure {:inline 1} send__success(from: Ref, to: Ref, amount: int) returns (success: bool);
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies revert;
modifies Balance;
procedure CorralChoice_IERC20(this: Ref);
modifies gas;
modifies now;
procedure CorralEntry_IERC20();
modifies Alloc;
modifies gas;
modifies now;
procedure CorralChoice_Context(this: Ref);
modifies gas;
modifies now;
procedure CorralEntry_Context();
modifies Alloc;
modifies gas;
modifies now;
procedure CorralChoice_SafeMath(this: Ref);
modifies gas;
modifies now;
procedure CorralEntry_SafeMath();
modifies Alloc;
modifies gas;
modifies now;
procedure CorralChoice_ERC20(this: Ref);
modifies gas;
modifies now;
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__balances0;
modifies _balances_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
procedure CorralEntry_ERC20();
modifies Alloc;
modifies gas;
modifies now;
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__balances0;
modifies _balances_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
procedure CorralChoice_ERC20Detailed(this: Ref);
modifies gas;
modifies now;
procedure CorralEntry_ERC20Detailed();
modifies Alloc;
modifies gas;
modifies now;
procedure CorralChoice_ERC20Burnable(this: Ref);
modifies gas;
modifies now;
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__balances0;
modifies _balances_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
modifies _totalSupply_ERC20;
procedure CorralEntry_ERC20Burnable();
modifies Alloc;
modifies gas;
modifies now;
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__balances0;
modifies _balances_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
modifies _totalSupply_ERC20;
procedure CorralChoice_BoltToken(this: Ref);
modifies gas;
modifies now;
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__balances0;
modifies _balances_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
modifies _totalSupply_ERC20;
procedure main();
modifies Alloc;
modifies gas;
modifies now;
modifies revert;
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies sum__balances0;
modifies _balances_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
modifies _totalSupply_ERC20;
var __tmp__Balance: [Ref]int;
var __tmp__DType: [Ref]ContractName;
var __tmp__Alloc: [Ref]bool;
var __tmp__balance_ADDR: [Ref]int;
var __tmp__M_Ref_int: [Ref][Ref]int;
var __tmp__sum__balances0: [Ref]int;
var __tmp__alloc__allowances_ERC20_lvl0: [Ref][Ref]bool;
var __tmp__M_Ref_Ref: [Ref][Ref]Ref;
var __tmp__sum__allowances1: [Ref]int;
var __tmp__Length: [Ref]int;
var __tmp__now: int;
var __tmp___balances_ERC20: [Ref][Ref]int;
var __tmp___allowances_ERC20: [Ref][Ref][Ref]int;
var __tmp___totalSupply_ERC20: [Ref]int;
var __tmp___name_ERC20Detailed: [Ref]int;
var __tmp___symbol_ERC20Detailed: [Ref]int;
var __tmp___decimals_ERC20Detailed: [Ref]int;
procedure {:inline 1} FreshRefGenerator__fail() returns (newRef: Ref);
modifies __tmp__Alloc;
procedure {:inline 1} _msgSender_Context__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: Ref);
procedure {:inline 1} add~uint256~uint256_SafeMath__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s107: int, b_s107: int) returns (__ret_0_: int);
modifies revert;
procedure {:inline 1} sub~uint256~uint256_SafeMath__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s123: int, b_s123: int) returns (__ret_0_: int);
modifies revert;
procedure {:inline 1} sub~uint256~uint256~string_SafeMath__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s150: int, b_s150: int, errorMessage_s150: int) returns (__ret_0_: int);
modifies revert;
procedure {:inline 1} totalSupply_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int);
procedure {:inline 1} totalSupply_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int);
procedure {:inline 1} balanceOf~address_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s306: Ref) returns (__ret_0_: int);
procedure {:inline 1} balanceOf~address_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s306: Ref) returns (__ret_0_: int);
procedure {:inline 1} transfer~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, recipient_s325: Ref, amount_s325: int) returns (__ret_0_: bool);
modifies revert;
modifies sum__balances0;
modifies _balances_ERC20;
procedure {:inline 1} transfer~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, recipient_s325: Ref, amount_s325: int) returns (__ret_0_: bool);
modifies revert;
modifies __tmp__sum__balances0;
modifies __tmp___balances_ERC20;
procedure {:inline 1} allowance~address~address_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, owner_s341: Ref, spender_s341: Ref) returns (__ret_0_: int);
procedure {:inline 1} allowance~address~address_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, owner_s341: Ref, spender_s341: Ref) returns (__ret_0_: int);
procedure {:inline 1} approve~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s360: Ref, amount_s360: int) returns (__ret_0_: bool);
modifies revert;
modifies sum__allowances1;
modifies _allowances_ERC20;
procedure {:inline 1} approve~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s360: Ref, amount_s360: int) returns (__ret_0_: bool);
modifies revert;
modifies __tmp__sum__allowances1;
modifies __tmp___allowances_ERC20;
procedure {:inline 1} transferFrom~address~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, sender_s396: Ref, recipient_s396: Ref, amount_s396: int) returns (__ret_0_: bool);
modifies revert;
modifies sum__balances0;
modifies _balances_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
procedure {:inline 1} transferFrom~address~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, sender_s396: Ref, recipient_s396: Ref, amount_s396: int) returns (__ret_0_: bool);
modifies revert;
modifies __tmp__sum__balances0;
modifies __tmp___balances_ERC20;
modifies __tmp__sum__allowances1;
modifies __tmp___allowances_ERC20;
procedure {:inline 1} increaseAllowance~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s423: Ref, addedValue_s423: int) returns (__ret_0_: bool);
modifies revert;
modifies sum__allowances1;
modifies _allowances_ERC20;
procedure {:inline 1} increaseAllowance~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s423: Ref, addedValue_s423: int) returns (__ret_0_: bool);
modifies revert;
modifies __tmp__sum__allowances1;
modifies __tmp___allowances_ERC20;
procedure {:inline 1} decreaseAllowance~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s451: Ref, subtractedValue_s451: int) returns (__ret_0_: bool);
modifies revert;
modifies sum__allowances1;
modifies _allowances_ERC20;
procedure {:inline 1} decreaseAllowance~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s451: Ref, subtractedValue_s451: int) returns (__ret_0_: bool);
modifies revert;
modifies __tmp__sum__allowances1;
modifies __tmp___allowances_ERC20;
procedure {:inline 1} _transfer~address~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, sender_s508: Ref, recipient_s508: Ref, amount_s508: int);
modifies revert;
modifies __tmp__sum__balances0;
modifies __tmp___balances_ERC20;
procedure {:inline 1} _burn~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s595: Ref, amount_s595: int);
modifies revert;
modifies __tmp__sum__balances0;
modifies __tmp___balances_ERC20;
modifies __tmp___totalSupply_ERC20;
procedure {:inline 1} _approve~address~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, owner_s637: Ref, spender_s637: Ref, amount_s637: int);
modifies revert;
modifies __tmp__sum__allowances1;
modifies __tmp___allowances_ERC20;
procedure {:inline 1} _burnFrom~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s666: Ref, amount_s666: int);
modifies revert;
modifies __tmp__sum__balances0;
modifies __tmp___balances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp__sum__allowances1;
modifies __tmp___allowances_ERC20;
procedure {:inline 1} burn~uint256_ERC20Burnable__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, amount_s738: int);
modifies revert;
modifies sum__balances0;
modifies _balances_ERC20;
modifies _totalSupply_ERC20;
procedure {:inline 1} burn~uint256_ERC20Burnable__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, amount_s738: int);
modifies revert;
modifies __tmp__sum__balances0;
modifies __tmp___balances_ERC20;
modifies __tmp___totalSupply_ERC20;
procedure {:inline 1} burnFrom~address~uint256_ERC20Burnable__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s751: Ref, amount_s751: int);
modifies revert;
modifies sum__balances0;
modifies _balances_ERC20;
modifies _totalSupply_ERC20;
modifies sum__allowances1;
modifies _allowances_ERC20;
procedure {:inline 1} burnFrom~address~uint256_ERC20Burnable__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s751: Ref, amount_s751: int);
modifies revert;
modifies __tmp__sum__balances0;
modifies __tmp___balances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp__sum__allowances1;
modifies __tmp___allowances_ERC20;
procedure {:inline 1} FallbackDispatch__fail(from: Ref, to: Ref, amount: int);
procedure {:inline 1} Fallback_UnknownType__fail(from: Ref, to: Ref, amount: int);
procedure {:inline 1} send__fail(from: Ref, to: Ref, amount: int) returns (success: bool);
modifies __tmp__Balance;
modifies __tmp__DType;
modifies __tmp__Alloc;
modifies __tmp__balance_ADDR;
modifies __tmp__M_Ref_int;
modifies __tmp__sum__balances0;
modifies __tmp__alloc__allowances_ERC20_lvl0;
modifies __tmp__M_Ref_Ref;
modifies __tmp__sum__allowances1;
modifies __tmp__Length;
modifies __tmp__now;
modifies __tmp___balances_ERC20;
modifies __tmp___allowances_ERC20;
modifies __tmp___totalSupply_ERC20;
modifies __tmp___name_ERC20Detailed;
modifies __tmp___symbol_ERC20Detailed;
modifies __tmp___decimals_ERC20Detailed;
modifies revert;
implementation FreshRefGenerator__fail() returns (newRef: Ref)
{
havoc newRef;
assume ((__tmp__Alloc[newRef]) == (false));
__tmp__Alloc[newRef] := true;
assume ((newRef) != (null));
}

implementation FreshRefGenerator__success() returns (newRef: Ref)
{
havoc newRef;
assume ((Alloc[newRef]) == (false));
Alloc[newRef] := true;
assume ((newRef) != (null));
}

implementation _msgSender_Context__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: Ref)
{
__ret_0_ := msgsender_MSG;
return;
}

implementation _msgSender_Context__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: Ref)
{
__ret_0_ := msgsender_MSG;
return;
}

implementation add~uint256~uint256_SafeMath__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s107: int, b_s107: int) returns (__ret_0_: int)
{
var c_s107: int;
c_s107 := (a_s107) + (b_s107);
if (!((c_s107) >= (a_s107))) {
revert := true;
return;
}
__ret_0_ := c_s107;
return;
}

implementation add~uint256~uint256_SafeMath__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s107: int, b_s107: int) returns (__ret_0_: int)
{
var c_s107: int;
c_s107 := (a_s107) + (b_s107);
if (!((c_s107) >= (a_s107))) {
revert := true;
return;
}
__ret_0_ := c_s107;
return;
}

implementation sub~uint256~uint256_SafeMath__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s123: int, b_s123: int) returns (__ret_0_: int)
{
var __var_1: int;
call __var_1 := sub~uint256~uint256~string_SafeMath__fail(this, msgsender_MSG, msgvalue_MSG, a_s123, b_s123, -1119389699);
if (revert) {
return;
}
__ret_0_ := __var_1;
return;
}

implementation sub~uint256~uint256_SafeMath__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s123: int, b_s123: int) returns (__ret_0_: int)
{
var __var_1: int;
call __var_1 := sub~uint256~uint256~string_SafeMath__success(this, msgsender_MSG, msgvalue_MSG, a_s123, b_s123, -1119389699);
if (revert) {
return;
}
__ret_0_ := __var_1;
return;
}

implementation sub~uint256~uint256~string_SafeMath__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s150: int, b_s150: int, errorMessage_s150: int) returns (__ret_0_: int)
{
var c_s150: int;
if (!((b_s150) <= (a_s150))) {
revert := true;
return;
}
c_s150 := (a_s150) - (b_s150);
__ret_0_ := c_s150;
return;
}

implementation sub~uint256~uint256~string_SafeMath__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, a_s150: int, b_s150: int, errorMessage_s150: int) returns (__ret_0_: int)
{
var c_s150: int;
if (!((b_s150) <= (a_s150))) {
revert := true;
return;
}
c_s150 := (a_s150) - (b_s150);
__ret_0_ := c_s150;
return;
}

implementation totalSupply_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int)
{
__ret_0_ := __tmp___totalSupply_ERC20[this];
return;
}

implementation totalSupply_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int) returns (__ret_0_: int)
{
__ret_0_ := _totalSupply_ERC20[this];
return;
}

implementation balanceOf~address_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s306: Ref) returns (__ret_0_: int)
{
__ret_0_ := __tmp___balances_ERC20[this][account_s306];
return;
}

implementation balanceOf~address_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s306: Ref) returns (__ret_0_: int)
{
__ret_0_ := _balances_ERC20[this][account_s306];
return;
}

implementation transfer~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, recipient_s325: Ref, amount_s325: int) returns (__ret_0_: bool)
{
var __var_2: Ref;
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_2 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_2 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call __var_2 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((__tmp__DType[this]) == (BoltToken)) {
call _transfer~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_2, recipient_s325, amount_s325);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call _transfer~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_2, recipient_s325, amount_s325);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call _transfer~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_2, recipient_s325, amount_s325);
if (revert) {
return;
}
} else {
assume (false);
}
__ret_0_ := true;
return;
}

implementation transfer~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, recipient_s325: Ref, amount_s325: int) returns (__ret_0_: bool)
{
var __var_2: Ref;
if ((DType[this]) == (BoltToken)) {
call __var_2 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_2 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call __var_2 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((DType[this]) == (BoltToken)) {
call _transfer~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_2, recipient_s325, amount_s325);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call _transfer~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_2, recipient_s325, amount_s325);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call _transfer~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_2, recipient_s325, amount_s325);
if (revert) {
return;
}
} else {
assume (false);
}
__ret_0_ := true;
return;
}

implementation allowance~address~address_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, owner_s341: Ref, spender_s341: Ref) returns (__ret_0_: int)
{
__ret_0_ := __tmp___allowances_ERC20[this][owner_s341][spender_s341];
return;
}

implementation allowance~address~address_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, owner_s341: Ref, spender_s341: Ref) returns (__ret_0_: int)
{
__ret_0_ := _allowances_ERC20[this][owner_s341][spender_s341];
return;
}

implementation approve~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s360: Ref, amount_s360: int) returns (__ret_0_: bool)
{
var __var_3: Ref;
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_3 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_3 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call __var_3 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((__tmp__DType[this]) == (BoltToken)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_3, spender_s360, amount_s360);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_3, spender_s360, amount_s360);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_3, spender_s360, amount_s360);
if (revert) {
return;
}
} else {
assume (false);
}
__ret_0_ := true;
return;
}

implementation approve~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s360: Ref, amount_s360: int) returns (__ret_0_: bool)
{
var __var_3: Ref;
if ((DType[this]) == (BoltToken)) {
call __var_3 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_3 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call __var_3 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((DType[this]) == (BoltToken)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_3, spender_s360, amount_s360);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_3, spender_s360, amount_s360);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_3, spender_s360, amount_s360);
if (revert) {
return;
}
} else {
assume (false);
}
__ret_0_ := true;
return;
}

implementation transferFrom~address~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, sender_s396: Ref, recipient_s396: Ref, amount_s396: int) returns (__ret_0_: bool)
{
var __var_4: Ref;
var __var_5: int;
var __var_6: Ref;
if ((__tmp__DType[this]) == (BoltToken)) {
call _transfer~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call _transfer~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call _transfer~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
if (revert) {
return;
}
} else {
assume (false);
}
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_4 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_4 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call __var_4 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_6 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_6 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call __var_6 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
call __var_5 := sub~uint256~uint256~string_SafeMath__fail(this, this, 0, __tmp___allowances_ERC20[this][sender_s396][__var_6], amount_s396, -1848603876);
if (revert) {
return;
}
if ((__tmp__DType[this]) == (BoltToken)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, sender_s396, __var_4, __var_5);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, sender_s396, __var_4, __var_5);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, sender_s396, __var_4, __var_5);
if (revert) {
return;
}
} else {
assume (false);
}
__ret_0_ := true;
return;
}

implementation transferFrom~address~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, sender_s396: Ref, recipient_s396: Ref, amount_s396: int) returns (__ret_0_: bool)
{
var __var_4: Ref;
var __var_5: int;
var __var_6: Ref;
if ((DType[this]) == (BoltToken)) {
call _transfer~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call _transfer~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call _transfer~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
if (revert) {
return;
}
} else {
assume (false);
}
if ((DType[this]) == (BoltToken)) {
call __var_4 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_4 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call __var_4 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((DType[this]) == (BoltToken)) {
call __var_6 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_6 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call __var_6 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
call __var_5 := sub~uint256~uint256~string_SafeMath__success(this, this, 0, _allowances_ERC20[this][sender_s396][__var_6], amount_s396, -1848603876);
if (revert) {
return;
}
if ((DType[this]) == (BoltToken)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, sender_s396, __var_4, __var_5);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, sender_s396, __var_4, __var_5);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, sender_s396, __var_4, __var_5);
if (revert) {
return;
}
} else {
assume (false);
}
__ret_0_ := true;
return;
}

implementation increaseAllowance~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s423: Ref, addedValue_s423: int) returns (__ret_0_: bool)
{
var __var_7: Ref;
var __var_8: int;
var __var_9: Ref;
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_7 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_7 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call __var_7 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_9 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_9 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call __var_9 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
call __var_8 := add~uint256~uint256_SafeMath__fail(this, this, 0, __tmp___allowances_ERC20[this][__var_9][spender_s423], addedValue_s423);
if (revert) {
return;
}
if ((__tmp__DType[this]) == (BoltToken)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_7, spender_s423, __var_8);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_7, spender_s423, __var_8);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_7, spender_s423, __var_8);
if (revert) {
return;
}
} else {
assume (false);
}
__ret_0_ := true;
return;
}

implementation increaseAllowance~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s423: Ref, addedValue_s423: int) returns (__ret_0_: bool)
{
var __var_7: Ref;
var __var_8: int;
var __var_9: Ref;
if ((DType[this]) == (BoltToken)) {
call __var_7 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_7 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call __var_7 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((DType[this]) == (BoltToken)) {
call __var_9 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_9 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call __var_9 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
call __var_8 := add~uint256~uint256_SafeMath__success(this, this, 0, _allowances_ERC20[this][__var_9][spender_s423], addedValue_s423);
if (revert) {
return;
}
if ((DType[this]) == (BoltToken)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_7, spender_s423, __var_8);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_7, spender_s423, __var_8);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_7, spender_s423, __var_8);
if (revert) {
return;
}
} else {
assume (false);
}
__ret_0_ := true;
return;
}

implementation decreaseAllowance~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s451: Ref, subtractedValue_s451: int) returns (__ret_0_: bool)
{
var __var_10: Ref;
var __var_11: int;
var __var_12: Ref;
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_10 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_10 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call __var_10 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_12 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_12 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call __var_12 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
call __var_11 := sub~uint256~uint256~string_SafeMath__fail(this, this, 0, __tmp___allowances_ERC20[this][__var_12][spender_s451], subtractedValue_s451, -751998178);
if (revert) {
return;
}
if ((__tmp__DType[this]) == (BoltToken)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_10, spender_s451, __var_11);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_10, spender_s451, __var_11);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_10, spender_s451, __var_11);
if (revert) {
return;
}
} else {
assume (false);
}
__ret_0_ := true;
return;
}

implementation decreaseAllowance~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, spender_s451: Ref, subtractedValue_s451: int) returns (__ret_0_: bool)
{
var __var_10: Ref;
var __var_11: int;
var __var_12: Ref;
if ((DType[this]) == (BoltToken)) {
call __var_10 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_10 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call __var_10 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((DType[this]) == (BoltToken)) {
call __var_12 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_12 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call __var_12 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
call __var_11 := sub~uint256~uint256~string_SafeMath__success(this, this, 0, _allowances_ERC20[this][__var_12][spender_s451], subtractedValue_s451, -751998178);
if (revert) {
return;
}
if ((DType[this]) == (BoltToken)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_10, spender_s451, __var_11);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_10, spender_s451, __var_11);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_10, spender_s451, __var_11);
if (revert) {
return;
}
} else {
assume (false);
}
__ret_0_ := true;
return;
}

implementation _transfer~address~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, sender_s508: Ref, recipient_s508: Ref, amount_s508: int)
{
var __var_13: Ref;
var __var_14: Ref;
var __var_15: int;
var __var_16: int;
__var_13 := null;
if (!((sender_s508) != (null))) {
revert := true;
return;
}
__var_14 := null;
if (!((recipient_s508) != (null))) {
revert := true;
return;
}
call __var_15 := sub~uint256~uint256~string_SafeMath__fail(this, this, 0, __tmp___balances_ERC20[this][sender_s508], amount_s508, -1870368058);
if (revert) {
return;
}
__tmp__sum__balances0[this] := (__tmp__sum__balances0[this]) - (__tmp___balances_ERC20[this][sender_s508]);
__tmp___balances_ERC20[this][sender_s508] := __var_15;
__tmp__sum__balances0[this] := (__tmp__sum__balances0[this]) + (__tmp___balances_ERC20[this][sender_s508]);
call __var_16 := add~uint256~uint256_SafeMath__fail(this, this, 0, __tmp___balances_ERC20[this][recipient_s508], amount_s508);
if (revert) {
return;
}
__tmp__sum__balances0[this] := (__tmp__sum__balances0[this]) - (__tmp___balances_ERC20[this][recipient_s508]);
__tmp___balances_ERC20[this][recipient_s508] := __var_16;
__tmp__sum__balances0[this] := (__tmp__sum__balances0[this]) + (__tmp___balances_ERC20[this][recipient_s508]);
}

implementation _transfer~address~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, sender_s508: Ref, recipient_s508: Ref, amount_s508: int)
{
var __var_13: Ref;
var __var_14: Ref;
var __var_15: int;
var __var_16: int;
__var_13 := null;
if (!((sender_s508) != (null))) {
revert := true;
return;
}
__var_14 := null;
if (!((recipient_s508) != (null))) {
revert := true;
return;
}
call __var_15 := sub~uint256~uint256~string_SafeMath__success(this, this, 0, _balances_ERC20[this][sender_s508], amount_s508, -1870368058);
if (revert) {
return;
}
sum__balances0[this] := (sum__balances0[this]) - (_balances_ERC20[this][sender_s508]);
_balances_ERC20[this][sender_s508] := __var_15;
sum__balances0[this] := (sum__balances0[this]) + (_balances_ERC20[this][sender_s508]);
call __var_16 := add~uint256~uint256_SafeMath__success(this, this, 0, _balances_ERC20[this][recipient_s508], amount_s508);
if (revert) {
return;
}
sum__balances0[this] := (sum__balances0[this]) - (_balances_ERC20[this][recipient_s508]);
_balances_ERC20[this][recipient_s508] := __var_16;
sum__balances0[this] := (sum__balances0[this]) + (_balances_ERC20[this][recipient_s508]);
assert {:EventEmitted "Transfer_ERC20"} (true);
}

implementation _burn~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s595: Ref, amount_s595: int)
{
var __var_17: Ref;
var __var_18: int;
var __var_19: int;
__var_17 := null;
if (!((account_s595) != (null))) {
revert := true;
return;
}
call __var_18 := sub~uint256~uint256~string_SafeMath__fail(this, this, 0, __tmp___balances_ERC20[this][account_s595], amount_s595, -10195818);
if (revert) {
return;
}
__tmp__sum__balances0[this] := (__tmp__sum__balances0[this]) - (__tmp___balances_ERC20[this][account_s595]);
__tmp___balances_ERC20[this][account_s595] := __var_18;
__tmp__sum__balances0[this] := (__tmp__sum__balances0[this]) + (__tmp___balances_ERC20[this][account_s595]);
call __var_19 := sub~uint256~uint256_SafeMath__fail(this, this, 0, __tmp___totalSupply_ERC20[this], amount_s595);
if (revert) {
return;
}
__tmp___totalSupply_ERC20[this] := __var_19;
}

implementation _burn~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s595: Ref, amount_s595: int)
{
var __var_17: Ref;
var __var_18: int;
var __var_19: int;
__var_17 := null;
if (!((account_s595) != (null))) {
revert := true;
return;
}
call __var_18 := sub~uint256~uint256~string_SafeMath__success(this, this, 0, _balances_ERC20[this][account_s595], amount_s595, -10195818);
if (revert) {
return;
}
sum__balances0[this] := (sum__balances0[this]) - (_balances_ERC20[this][account_s595]);
_balances_ERC20[this][account_s595] := __var_18;
sum__balances0[this] := (sum__balances0[this]) + (_balances_ERC20[this][account_s595]);
call __var_19 := sub~uint256~uint256_SafeMath__success(this, this, 0, _totalSupply_ERC20[this], amount_s595);
if (revert) {
return;
}
_totalSupply_ERC20[this] := __var_19;
assert {:EventEmitted "Transfer_ERC20"} (true);
}

implementation _approve~address~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, owner_s637: Ref, spender_s637: Ref, amount_s637: int)
{
var __var_20: Ref;
var __var_21: Ref;
__var_20 := null;
if (!((owner_s637) != (null))) {
revert := true;
return;
}
__var_21 := null;
if (!((spender_s637) != (null))) {
revert := true;
return;
}
__tmp__sum__allowances1[owner_s637] := (__tmp__sum__allowances1[owner_s637]) - (__tmp___allowances_ERC20[this][owner_s637][spender_s637]);
__tmp___allowances_ERC20[this][owner_s637][spender_s637] := amount_s637;
__tmp__sum__allowances1[owner_s637] := (__tmp__sum__allowances1[owner_s637]) + (__tmp___allowances_ERC20[this][owner_s637][spender_s637]);
}

implementation _approve~address~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, owner_s637: Ref, spender_s637: Ref, amount_s637: int)
{
var __var_20: Ref;
var __var_21: Ref;
__var_20 := null;
if (!((owner_s637) != (null))) {
revert := true;
return;
}
__var_21 := null;
if (!((spender_s637) != (null))) {
revert := true;
return;
}
sum__allowances1[owner_s637] := (sum__allowances1[owner_s637]) - (_allowances_ERC20[this][owner_s637][spender_s637]);
_allowances_ERC20[this][owner_s637][spender_s637] := amount_s637;
sum__allowances1[owner_s637] := (sum__allowances1[owner_s637]) + (_allowances_ERC20[this][owner_s637][spender_s637]);
assert {:EventEmitted "Approval_ERC20"} (true);
}

implementation _burnFrom~address~uint256_ERC20__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s666: Ref, amount_s666: int)
{
var __var_22: Ref;
var __var_23: int;
var __var_24: Ref;
if ((__tmp__DType[this]) == (BoltToken)) {
call _burn~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, account_s666, amount_s666);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call _burn~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, account_s666, amount_s666);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call _burn~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, account_s666, amount_s666);
if (revert) {
return;
}
} else {
assume (false);
}
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_22 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_22 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call __var_22 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_24 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_24 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call __var_24 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
call __var_23 := sub~uint256~uint256~string_SafeMath__fail(this, this, 0, __tmp___allowances_ERC20[this][account_s666][__var_24], amount_s666, -1354707684);
if (revert) {
return;
}
if ((__tmp__DType[this]) == (BoltToken)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, account_s666, __var_22, __var_23);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, account_s666, __var_22, __var_23);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20)) {
call _approve~address~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, account_s666, __var_22, __var_23);
if (revert) {
return;
}
} else {
assume (false);
}
}

implementation _burnFrom~address~uint256_ERC20__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s666: Ref, amount_s666: int)
{
var __var_22: Ref;
var __var_23: int;
var __var_24: Ref;
if ((DType[this]) == (BoltToken)) {
call _burn~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, account_s666, amount_s666);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call _burn~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, account_s666, amount_s666);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call _burn~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, account_s666, amount_s666);
if (revert) {
return;
}
} else {
assume (false);
}
if ((DType[this]) == (BoltToken)) {
call __var_22 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_22 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call __var_22 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((DType[this]) == (BoltToken)) {
call __var_24 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_24 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call __var_24 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
call __var_23 := sub~uint256~uint256~string_SafeMath__success(this, this, 0, _allowances_ERC20[this][account_s666][__var_24], amount_s666, -1354707684);
if (revert) {
return;
}
if ((DType[this]) == (BoltToken)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, account_s666, __var_22, __var_23);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, account_s666, __var_22, __var_23);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20)) {
call _approve~address~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, account_s666, __var_22, __var_23);
if (revert) {
return;
}
} else {
assume (false);
}
}

implementation burn~uint256_ERC20Burnable__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, amount_s738: int)
{
var __var_25: Ref;
if ((__tmp__DType[this]) == (BoltToken)) {
call __var_25 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call __var_25 := _msgSender_Context__fail(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((__tmp__DType[this]) == (BoltToken)) {
call _burn~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_25, amount_s738);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call _burn~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, __var_25, amount_s738);
if (revert) {
return;
}
} else {
assume (false);
}
}

implementation burn~uint256_ERC20Burnable__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, amount_s738: int)
{
var __var_25: Ref;
if ((DType[this]) == (BoltToken)) {
call __var_25 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call __var_25 := _msgSender_Context__success(this, msgsender_MSG, msgvalue_MSG);
if (revert) {
return;
}
} else {
assume (false);
}
if ((DType[this]) == (BoltToken)) {
call _burn~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_25, amount_s738);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call _burn~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, __var_25, amount_s738);
if (revert) {
return;
}
} else {
assume (false);
}
}

implementation burnFrom~address~uint256_ERC20Burnable__fail(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s751: Ref, amount_s751: int)
{
if ((__tmp__DType[this]) == (BoltToken)) {
call _burnFrom~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, account_s751, amount_s751);
if (revert) {
return;
}
} else if ((__tmp__DType[this]) == (ERC20Burnable)) {
call _burnFrom~address~uint256_ERC20__fail(this, msgsender_MSG, msgvalue_MSG, account_s751, amount_s751);
if (revert) {
return;
}
} else {
assume (false);
}
}

implementation burnFrom~address~uint256_ERC20Burnable__success(this: Ref, msgsender_MSG: Ref, msgvalue_MSG: int, account_s751: Ref, amount_s751: int)
{
if ((DType[this]) == (BoltToken)) {
call _burnFrom~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, account_s751, amount_s751);
if (revert) {
return;
}
} else if ((DType[this]) == (ERC20Burnable)) {
call _burnFrom~address~uint256_ERC20__success(this, msgsender_MSG, msgvalue_MSG, account_s751, amount_s751);
if (revert) {
return;
}
} else {
assume (false);
}
}

implementation FallbackDispatch__fail(from: Ref, to: Ref, amount: int)
{
if ((__tmp__DType[to]) == (BoltToken)) {
assume ((amount) == (0));
} else if ((__tmp__DType[to]) == (ERC20Burnable)) {
assume ((amount) == (0));
} else if ((__tmp__DType[to]) == (ERC20Detailed)) {
assume ((amount) == (0));
} else if ((__tmp__DType[to]) == (ERC20)) {
assume ((amount) == (0));
} else if ((__tmp__DType[to]) == (Context)) {
assume ((amount) == (0));
} else if ((__tmp__DType[to]) == (IERC20)) {
assume ((amount) == (0));
} else {
call Fallback_UnknownType__fail(from, to, amount);
if (revert) {
return;
}
}
}

implementation FallbackDispatch__success(from: Ref, to: Ref, amount: int)
{
if ((DType[to]) == (BoltToken)) {
assume ((amount) == (0));
} else if ((DType[to]) == (ERC20Burnable)) {
assume ((amount) == (0));
} else if ((DType[to]) == (ERC20Detailed)) {
assume ((amount) == (0));
} else if ((DType[to]) == (ERC20)) {
assume ((amount) == (0));
} else if ((DType[to]) == (Context)) {
assume ((amount) == (0));
} else if ((DType[to]) == (IERC20)) {
assume ((amount) == (0));
} else {
call Fallback_UnknownType__success(from, to, amount);
if (revert) {
return;
}
}
}

implementation Fallback_UnknownType__fail(from: Ref, to: Ref, amount: int)
{
}

implementation Fallback_UnknownType__success(from: Ref, to: Ref, amount: int)
{
}

implementation send__fail(from: Ref, to: Ref, amount: int) returns (success: bool)
{
var __exception: bool;
var __snap___tmp__Balance: [Ref]int;
var __snap___tmp__DType: [Ref]ContractName;
var __snap___tmp__Alloc: [Ref]bool;
var __snap___tmp__balance_ADDR: [Ref]int;
var __snap___tmp__M_Ref_int: [Ref][Ref]int;
var __snap___tmp__sum__balances0: [Ref]int;
var __snap___tmp__alloc__allowances_ERC20_lvl0: [Ref][Ref]bool;
var __snap___tmp__M_Ref_Ref: [Ref][Ref]Ref;
var __snap___tmp__sum__allowances1: [Ref]int;
var __snap___tmp__Length: [Ref]int;
var __snap___tmp__now: int;
var __snap___tmp___balances_ERC20: [Ref][Ref]int;
var __snap___tmp___allowances_ERC20: [Ref][Ref][Ref]int;
var __snap___tmp___totalSupply_ERC20: [Ref]int;
var __snap___tmp___name_ERC20Detailed: [Ref]int;
var __snap___tmp___symbol_ERC20Detailed: [Ref]int;
var __snap___tmp___decimals_ERC20Detailed: [Ref]int;
havoc __exception;
if (__exception) {
__snap___tmp__Balance := __tmp__Balance;
__snap___tmp__DType := __tmp__DType;
__snap___tmp__Alloc := __tmp__Alloc;
__snap___tmp__balance_ADDR := __tmp__balance_ADDR;
__snap___tmp__M_Ref_int := __tmp__M_Ref_int;
__snap___tmp__sum__balances0 := __tmp__sum__balances0;
__snap___tmp__alloc__allowances_ERC20_lvl0 := __tmp__alloc__allowances_ERC20_lvl0;
__snap___tmp__M_Ref_Ref := __tmp__M_Ref_Ref;
__snap___tmp__sum__allowances1 := __tmp__sum__allowances1;
__snap___tmp__Length := __tmp__Length;
__snap___tmp__now := __tmp__now;
__snap___tmp___balances_ERC20 := __tmp___balances_ERC20;
__snap___tmp___allowances_ERC20 := __tmp___allowances_ERC20;
__snap___tmp___totalSupply_ERC20 := __tmp___totalSupply_ERC20;
__snap___tmp___name_ERC20Detailed := __tmp___name_ERC20Detailed;
__snap___tmp___symbol_ERC20Detailed := __tmp___symbol_ERC20Detailed;
__snap___tmp___decimals_ERC20Detailed := __tmp___decimals_ERC20Detailed;
if ((__tmp__Balance[from]) >= (amount)) {
// ---- Logic for payable function START 
__tmp__Balance[from] := (__tmp__Balance[from]) - (amount);
__tmp__Balance[to] := (__tmp__Balance[to]) + (amount);
// ---- Logic for payable function END 
call FallbackDispatch__fail(from, to, amount);
}
success := false;
assume ((revert) || ((gas) < (0)));
__tmp__Balance := __snap___tmp__Balance;
__tmp__DType := __snap___tmp__DType;
__tmp__Alloc := __snap___tmp__Alloc;
__tmp__balance_ADDR := __snap___tmp__balance_ADDR;
__tmp__M_Ref_int := __snap___tmp__M_Ref_int;
__tmp__sum__balances0 := __snap___tmp__sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := __snap___tmp__alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := __snap___tmp__M_Ref_Ref;
__tmp__sum__allowances1 := __snap___tmp__sum__allowances1;
__tmp__Length := __snap___tmp__Length;
__tmp__now := __snap___tmp__now;
__tmp___balances_ERC20 := __snap___tmp___balances_ERC20;
__tmp___allowances_ERC20 := __snap___tmp___allowances_ERC20;
__tmp___totalSupply_ERC20 := __snap___tmp___totalSupply_ERC20;
__tmp___name_ERC20Detailed := __snap___tmp___name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := __snap___tmp___symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := __snap___tmp___decimals_ERC20Detailed;
revert := false;
} else {
if ((__tmp__Balance[from]) >= (amount)) {
// ---- Logic for payable function START 
__tmp__Balance[from] := (__tmp__Balance[from]) - (amount);
__tmp__Balance[to] := (__tmp__Balance[to]) + (amount);
// ---- Logic for payable function END 
call FallbackDispatch__fail(from, to, amount);
success := true;
} else {
success := false;
}
assume ((!(revert)) && ((gas) >= (0)));
}
}

implementation send__success(from: Ref, to: Ref, amount: int) returns (success: bool)
{
var __exception: bool;
havoc __exception;
if (__exception) {
__tmp__Balance := Balance;
__tmp__DType := DType;
__tmp__Alloc := Alloc;
__tmp__balance_ADDR := balance_ADDR;
__tmp__M_Ref_int := M_Ref_int;
__tmp__sum__balances0 := sum__balances0;
__tmp__alloc__allowances_ERC20_lvl0 := alloc__allowances_ERC20_lvl0;
__tmp__M_Ref_Ref := M_Ref_Ref;
__tmp__sum__allowances1 := sum__allowances1;
__tmp__Length := Length;
__tmp__now := now;
__tmp___balances_ERC20 := _balances_ERC20;
__tmp___allowances_ERC20 := _allowances_ERC20;
__tmp___totalSupply_ERC20 := _totalSupply_ERC20;
__tmp___name_ERC20Detailed := _name_ERC20Detailed;
__tmp___symbol_ERC20Detailed := _symbol_ERC20Detailed;
__tmp___decimals_ERC20Detailed := _decimals_ERC20Detailed;
if ((__tmp__Balance[from]) >= (amount)) {
// ---- Logic for payable function START 
__tmp__Balance[from] := (__tmp__Balance[from]) - (amount);
__tmp__Balance[to] := (__tmp__Balance[to]) + (amount);
// ---- Logic for payable function END 
call FallbackDispatch__fail(from, to, amount);
}
success := false;
assume ((revert) || ((gas) < (0)));
revert := false;
} else {
if ((Balance[from]) >= (amount)) {
// ---- Logic for payable function START 
Balance[from] := (Balance[from]) - (amount);
Balance[to] := (Balance[to]) + (amount);
// ---- Logic for payable function END 
call FallbackDispatch__success(from, to, amount);
success := true;
} else {
success := false;
}
assume ((!(revert)) && ((gas) >= (0)));
}
}

implementation CorralChoice_IERC20(this: Ref)
{
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var tmpNow: int;
havoc msgsender_MSG;
havoc msgvalue_MSG;
havoc choice;
havoc tmpNow;
havoc gas;
assume (((gas) > (4000000)) && ((gas) <= (8000000)));
tmpNow := now;
havoc now;
assume ((now) > (tmpNow));
assume ((msgsender_MSG) != (null));
}

implementation CorralEntry_IERC20()
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
assume ((null) == (0));
call this := FreshRefGenerator__success();
assume ((now) >= (0));
assume ((((((DType[this]) == (IERC20)) || ((DType[this]) == (ERC20))) || ((DType[this]) == (ERC20Detailed))) || ((DType[this]) == (ERC20Burnable))) || ((DType[this]) == (BoltToken)));
assume ((!(revert)) && ((gas) >= (0)));
call CorralChoice_IERC20(this);
while (true)
{
}
}

implementation CorralChoice_Context(this: Ref)
{
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var tmpNow: int;
havoc msgsender_MSG;
havoc msgvalue_MSG;
havoc choice;
havoc tmpNow;
havoc gas;
assume (((gas) > (4000000)) && ((gas) <= (8000000)));
tmpNow := now;
havoc now;
assume ((now) > (tmpNow));
assume ((msgsender_MSG) != (null));
}

implementation CorralEntry_Context()
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
assume ((null) == (0));
call this := FreshRefGenerator__success();
assume ((now) >= (0));
assume (((((DType[this]) == (Context)) || ((DType[this]) == (ERC20))) || ((DType[this]) == (ERC20Burnable))) || ((DType[this]) == (BoltToken)));
assume ((!(revert)) && ((gas) >= (0)));
call CorralChoice_Context(this);
while (true)
{
}
}

implementation CorralChoice_SafeMath(this: Ref)
{
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var tmpNow: int;
havoc msgsender_MSG;
havoc msgvalue_MSG;
havoc choice;
havoc tmpNow;
havoc gas;
assume (((gas) > (4000000)) && ((gas) <= (8000000)));
tmpNow := now;
havoc now;
assume ((now) > (tmpNow));
assume ((msgsender_MSG) != (null));
}

implementation CorralEntry_SafeMath()
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
assume ((null) == (0));
call this := FreshRefGenerator__success();
assume ((now) >= (0));
assume ((DType[this]) == (SafeMath));
assume ((!(revert)) && ((gas) >= (0)));
call CorralChoice_SafeMath(this);
while (true)
{
}
}

implementation CorralChoice_ERC20(this: Ref)
{
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var __ret_0_totalSupply: int;
var account_s306: Ref;
var __ret_0_balanceOf: int;
var recipient_s325: Ref;
var amount_s325: int;
var __ret_0_transfer: bool;
var owner_s341: Ref;
var spender_s341: Ref;
var __ret_0_allowance: int;
var spender_s360: Ref;
var amount_s360: int;
var __ret_0_approve: bool;
var sender_s396: Ref;
var recipient_s396: Ref;
var amount_s396: int;
var __ret_0_transferFrom: bool;
var spender_s423: Ref;
var addedValue_s423: int;
var __ret_0_increaseAllowance: bool;
var spender_s451: Ref;
var subtractedValue_s451: int;
var __ret_0_decreaseAllowance: bool;
var tmpNow: int;
havoc msgsender_MSG;
havoc msgvalue_MSG;
havoc choice;
havoc __ret_0_totalSupply;
havoc account_s306;
havoc __ret_0_balanceOf;
havoc recipient_s325;
havoc amount_s325;
havoc __ret_0_transfer;
havoc owner_s341;
havoc spender_s341;
havoc __ret_0_allowance;
havoc spender_s360;
havoc amount_s360;
havoc __ret_0_approve;
havoc sender_s396;
havoc recipient_s396;
havoc amount_s396;
havoc __ret_0_transferFrom;
havoc spender_s423;
havoc addedValue_s423;
havoc __ret_0_increaseAllowance;
havoc spender_s451;
havoc subtractedValue_s451;
havoc __ret_0_decreaseAllowance;
havoc tmpNow;
havoc gas;
assume (((gas) > (4000000)) && ((gas) <= (8000000)));
tmpNow := now;
havoc now;
assume ((now) > (tmpNow));
assume ((msgsender_MSG) != (null));
if ((choice) == (8)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_totalSupply := totalSupply_ERC20(this, msgsender_MSG, msgvalue_MSG);
}
} else if ((choice) == (7)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_balanceOf := balanceOf~address_ERC20(this, msgsender_MSG, msgvalue_MSG, account_s306);
}
} else if ((choice) == (6)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_transfer := transfer~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, recipient_s325, amount_s325);
}
} else if ((choice) == (5)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_allowance := allowance~address~address_ERC20(this, msgsender_MSG, msgvalue_MSG, owner_s341, spender_s341);
}
} else if ((choice) == (4)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_approve := approve~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, spender_s360, amount_s360);
}
} else if ((choice) == (3)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_transferFrom := transferFrom~address~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
}
} else if ((choice) == (2)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_increaseAllowance := increaseAllowance~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, spender_s423, addedValue_s423);
}
} else if ((choice) == (1)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_decreaseAllowance := decreaseAllowance~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, spender_s451, subtractedValue_s451);
}
}
}

implementation CorralEntry_ERC20()
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
assume ((null) == (0));
call this := FreshRefGenerator__success();
assume ((now) >= (0));
assume ((((DType[this]) == (ERC20)) || ((DType[this]) == (ERC20Burnable))) || ((DType[this]) == (BoltToken)));
assume ((!(revert)) && ((gas) >= (0)));
call CorralChoice_ERC20(this);
while (true)
{
}
}

implementation CorralChoice_ERC20Detailed(this: Ref)
{
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var __ret_0_totalSupply: int;
var account_s13: Ref;
var __ret_0_balanceOf: int;
var recipient_s22: Ref;
var amount_s22: int;
var __ret_0_transfer: bool;
var owner_s31: Ref;
var spender_s31: Ref;
var __ret_0_allowance: int;
var spender_s40: Ref;
var amount_s40: int;
var __ret_0_approve: bool;
var sender_s51: Ref;
var recipient_s51: Ref;
var amount_s51: int;
var __ret_0_transferFrom: bool;
var name_s697: int;
var symbol_s697: int;
var decimals_s697: int;
var __ret_0_name: int;
var __ret_0_symbol: int;
var __ret_0_decimals: int;
var tmpNow: int;
havoc msgsender_MSG;
havoc msgvalue_MSG;
havoc choice;
havoc __ret_0_totalSupply;
havoc account_s13;
havoc __ret_0_balanceOf;
havoc recipient_s22;
havoc amount_s22;
havoc __ret_0_transfer;
havoc owner_s31;
havoc spender_s31;
havoc __ret_0_allowance;
havoc spender_s40;
havoc amount_s40;
havoc __ret_0_approve;
havoc sender_s51;
havoc recipient_s51;
havoc amount_s51;
havoc __ret_0_transferFrom;
havoc name_s697;
havoc symbol_s697;
havoc decimals_s697;
havoc __ret_0_name;
havoc __ret_0_symbol;
havoc __ret_0_decimals;
havoc tmpNow;
havoc gas;
assume (((gas) > (4000000)) && ((gas) <= (8000000)));
tmpNow := now;
havoc now;
assume ((now) > (tmpNow));
assume ((msgsender_MSG) != (null));
}

implementation CorralEntry_ERC20Detailed()
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var name_s697: int;
var symbol_s697: int;
var decimals_s697: int;
assume ((null) == (0));
call this := FreshRefGenerator__success();
assume ((now) >= (0));
assume (((DType[this]) == (ERC20Detailed)) || ((DType[this]) == (BoltToken)));
assume ((!(revert)) && ((gas) >= (0)));
call CorralChoice_ERC20Detailed(this);
while (true)
{
}
}

implementation CorralChoice_ERC20Burnable(this: Ref)
{
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var __ret_0_totalSupply: int;
var account_s306: Ref;
var __ret_0_balanceOf: int;
var recipient_s325: Ref;
var amount_s325: int;
var __ret_0_transfer: bool;
var owner_s341: Ref;
var spender_s341: Ref;
var __ret_0_allowance: int;
var spender_s360: Ref;
var amount_s360: int;
var __ret_0_approve: bool;
var sender_s396: Ref;
var recipient_s396: Ref;
var amount_s396: int;
var __ret_0_transferFrom: bool;
var spender_s423: Ref;
var addedValue_s423: int;
var __ret_0_increaseAllowance: bool;
var spender_s451: Ref;
var subtractedValue_s451: int;
var __ret_0_decreaseAllowance: bool;
var amount_s738: int;
var account_s751: Ref;
var amount_s751: int;
var tmpNow: int;
havoc msgsender_MSG;
havoc msgvalue_MSG;
havoc choice;
havoc __ret_0_totalSupply;
havoc account_s306;
havoc __ret_0_balanceOf;
havoc recipient_s325;
havoc amount_s325;
havoc __ret_0_transfer;
havoc owner_s341;
havoc spender_s341;
havoc __ret_0_allowance;
havoc spender_s360;
havoc amount_s360;
havoc __ret_0_approve;
havoc sender_s396;
havoc recipient_s396;
havoc amount_s396;
havoc __ret_0_transferFrom;
havoc spender_s423;
havoc addedValue_s423;
havoc __ret_0_increaseAllowance;
havoc spender_s451;
havoc subtractedValue_s451;
havoc __ret_0_decreaseAllowance;
havoc amount_s738;
havoc account_s751;
havoc amount_s751;
havoc tmpNow;
havoc gas;
assume (((gas) > (4000000)) && ((gas) <= (8000000)));
tmpNow := now;
havoc now;
assume ((now) > (tmpNow));
assume ((msgsender_MSG) != (null));
if ((choice) == (10)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_totalSupply := totalSupply_ERC20(this, msgsender_MSG, msgvalue_MSG);
}
} else if ((choice) == (9)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_balanceOf := balanceOf~address_ERC20(this, msgsender_MSG, msgvalue_MSG, account_s306);
}
} else if ((choice) == (8)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_transfer := transfer~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, recipient_s325, amount_s325);
}
} else if ((choice) == (7)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_allowance := allowance~address~address_ERC20(this, msgsender_MSG, msgvalue_MSG, owner_s341, spender_s341);
}
} else if ((choice) == (6)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_approve := approve~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, spender_s360, amount_s360);
}
} else if ((choice) == (5)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_transferFrom := transferFrom~address~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
}
} else if ((choice) == (4)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_increaseAllowance := increaseAllowance~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, spender_s423, addedValue_s423);
}
} else if ((choice) == (3)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_decreaseAllowance := decreaseAllowance~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, spender_s451, subtractedValue_s451);
}
} else if ((choice) == (2)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call burn~uint256_ERC20Burnable(this, msgsender_MSG, msgvalue_MSG, amount_s738);
}
} else if ((choice) == (1)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call burnFrom~address~uint256_ERC20Burnable(this, msgsender_MSG, msgvalue_MSG, account_s751, amount_s751);
}
}
}

implementation CorralEntry_ERC20Burnable()
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
assume ((null) == (0));
call this := FreshRefGenerator__success();
assume ((now) >= (0));
assume (((DType[this]) == (ERC20Burnable)) || ((DType[this]) == (BoltToken)));
assume ((!(revert)) && ((gas) >= (0)));
call CorralChoice_ERC20Burnable(this);
while (true)
{
}
}

implementation CorralChoice_BoltToken(this: Ref)
{
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
var choice: int;
var __ret_0_totalSupply: int;
var account_s306: Ref;
var __ret_0_balanceOf: int;
var recipient_s325: Ref;
var amount_s325: int;
var __ret_0_transfer: bool;
var owner_s341: Ref;
var spender_s341: Ref;
var __ret_0_allowance: int;
var spender_s360: Ref;
var amount_s360: int;
var __ret_0_approve: bool;
var sender_s396: Ref;
var recipient_s396: Ref;
var amount_s396: int;
var __ret_0_transferFrom: bool;
var spender_s423: Ref;
var addedValue_s423: int;
var __ret_0_increaseAllowance: bool;
var spender_s451: Ref;
var subtractedValue_s451: int;
var __ret_0_decreaseAllowance: bool;
var name_s697: int;
var symbol_s697: int;
var decimals_s697: int;
var __ret_0_name: int;
var __ret_0_symbol: int;
var __ret_0_decimals: int;
var amount_s738: int;
var account_s751: Ref;
var amount_s751: int;
var tmpNow: int;
havoc msgsender_MSG;
havoc msgvalue_MSG;
havoc choice;
havoc __ret_0_totalSupply;
havoc account_s306;
havoc __ret_0_balanceOf;
havoc recipient_s325;
havoc amount_s325;
havoc __ret_0_transfer;
havoc owner_s341;
havoc spender_s341;
havoc __ret_0_allowance;
havoc spender_s360;
havoc amount_s360;
havoc __ret_0_approve;
havoc sender_s396;
havoc recipient_s396;
havoc amount_s396;
havoc __ret_0_transferFrom;
havoc spender_s423;
havoc addedValue_s423;
havoc __ret_0_increaseAllowance;
havoc spender_s451;
havoc subtractedValue_s451;
havoc __ret_0_decreaseAllowance;
havoc name_s697;
havoc symbol_s697;
havoc decimals_s697;
havoc __ret_0_name;
havoc __ret_0_symbol;
havoc __ret_0_decimals;
havoc amount_s738;
havoc account_s751;
havoc amount_s751;
havoc tmpNow;
havoc gas;
assume (((gas) > (4000000)) && ((gas) <= (8000000)));
tmpNow := now;
havoc now;
assume ((now) > (tmpNow));
assume ((msgsender_MSG) != (null));
if ((choice) == (10)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_totalSupply := totalSupply_ERC20(this, msgsender_MSG, msgvalue_MSG);
}
} else if ((choice) == (9)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_balanceOf := balanceOf~address_ERC20(this, msgsender_MSG, msgvalue_MSG, account_s306);
}
} else if ((choice) == (8)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_transfer := transfer~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, recipient_s325, amount_s325);
}
} else if ((choice) == (7)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_allowance := allowance~address~address_ERC20(this, msgsender_MSG, msgvalue_MSG, owner_s341, spender_s341);
}
} else if ((choice) == (6)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_approve := approve~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, spender_s360, amount_s360);
}
} else if ((choice) == (5)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_transferFrom := transferFrom~address~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, sender_s396, recipient_s396, amount_s396);
}
} else if ((choice) == (4)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_increaseAllowance := increaseAllowance~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, spender_s423, addedValue_s423);
}
} else if ((choice) == (3)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call __ret_0_decreaseAllowance := decreaseAllowance~address~uint256_ERC20(this, msgsender_MSG, msgvalue_MSG, spender_s451, subtractedValue_s451);
}
} else if ((choice) == (2)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call burn~uint256_ERC20Burnable(this, msgsender_MSG, msgvalue_MSG, amount_s738);
}
} else if ((choice) == (1)) {
gas := (gas) - (21000);
if ((gas) >= (0)) {
assume ((msgvalue_MSG) == (0));
call burnFrom~address~uint256_ERC20Burnable(this, msgsender_MSG, msgvalue_MSG, account_s751, amount_s751);
}
}
}

implementation main()
{
var this: Ref;
var msgsender_MSG: Ref;
var msgvalue_MSG: int;
assume ((null) == (0));
call this := FreshRefGenerator__success();
assume ((now) >= (0));
assume ((DType[this]) == (BoltToken));
assume ((!(revert)) && ((gas) >= (0)));
call CorralChoice_BoltToken(this);
while (true)
{
}
}


