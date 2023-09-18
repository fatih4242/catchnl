//
//  Login.swift
//  catchnl
//
//  Created by Fatih Toker on 14.09.2023.
//

import Foundation

struct ErrorMessageUserID: Hashable, Codable {
    var error: Int
    var message: String
    var userID: String?
}
